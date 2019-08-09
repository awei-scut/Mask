package com.example.awei.mask.adapter;

import android.content.Context;
import android.icu.util.ValueIterator;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.awei.mask.R;
import com.example.awei.mask.bean.Vedio;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;


public class RecentAdapter extends BaseAdapter {

    private Context context;
    private List<Vedio> data;
    private ViewHolder viewHolder;
    public RecentAdapter(Context context, List<Vedio> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_recently_one, null);
            viewHolder = new ViewHolder();
            viewHolder.status = view.findViewById(R.id.status);
            viewHolder.time = view.findViewById(R.id.time);
            viewHolder.player = view.findViewById(R.id.return_vedio);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        if(data.get(i).getStatus().equals("")){
            viewHolder.status.setText(data.get(i).getMode() + "后台处理中...");
            viewHolder.status.setTextColor(0xffff0000);
            viewHolder.time.setText("上传时间:"+ data.get(i).getTime());
            viewHolder.player.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.player.setVisibility(View.VISIBLE);
            viewHolder.status.setText("处理成功");
            viewHolder.status.setTextColor(0xff00ff00);
            viewHolder.time.setText("上传时间："+ data.get(i).getTime());
            viewHolder.player.setUp(data.get(i).getPath(), true, data.get(i).getName());
            viewHolder.player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.player.startWindowFullscreen(context, false, true);
                }
            });
        }
        return view;
    }

    class ViewHolder{
        TextView status;
        TextView time;
        StandardGSYVideoPlayer player;
    }
}
