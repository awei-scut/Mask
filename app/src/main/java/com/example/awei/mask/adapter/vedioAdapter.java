package com.example.awei.mask.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.awei.mask.R;
import com.example.awei.mask.bean.Vedio;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class vedioAdapter extends BaseAdapter {
    private Context context;
    private List<Vedio> vedios;
    public static class ViewHolder{
        TextView tvTime;
        StandardGSYVideoPlayer player;
    }
    public ViewHolder holder;
    public vedioAdapter(Context context, List<Vedio> vedios){
        holder = null;
        this.context = context;
        this.vedios = vedios;
    }

    @Override
    public int getCount() {
        return vedios.size();
    }

    @Override
    public Object getItem(int i) {
        return vedios.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.vedio_item, null);
            holder = new ViewHolder();
            holder.tvTime = view.findViewById(R.id.time);
            holder.player = view.findViewById(R.id.vedio);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvTime.setText("发布时间:" + vedios.get(i).getTime());
        holder.player.setUp(vedios.get(i).getPath(), true, vedios.get(i).getTitle());
//        ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(imgs.get(i));
//        holder.player.setThumbImageView(imageView);
//        orientationUtils = new OrientationUtils((Activity) context, holder.player);
        holder.player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.player.startWindowFullscreen(context, false, true);
            }
        });
        holder.player.getBackButton().setVisibility(View.GONE);
        return view;
    }
}
