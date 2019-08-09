package com.example.awei.mask.Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.ListView;
import android.widget.Toast;

import com.example.awei.mask.R;
import com.example.awei.mask.adapter.RecentAdapter;
import com.example.awei.mask.bean.Vedio;
import com.example.awei.mask.utils.HttpHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecentFrag extends Fragment {

    private List<Vedio> data = new ArrayList<>();
    private ListView listView;
    private RecentAdapter ra;
    private View view;
    private RefreshLayout reflash;

    private final static String URL_FACE = "http://dianshemask3.natapp1.cc/file/";
    private final static String URL_TRANSFER = "http://dianshemask4.natapp1.cc/file/";
    private final static String URL_SOUND = "http://dianshemask4.natapp1.cc/file/";
    private String url = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recent_layout, container, false);
        listView = view.findViewById(R.id.result_lv);
        reflash = view.findViewById(R.id.reflash);
        ra = new RecentAdapter(getActivity() , data);
        listView.setAdapter(ra);
        reflash.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new MyAsynTask().execute();
                reflash.finishRefresh(1000,true);//传入false表示刷新失败
            }
        });

        return view;
    }
    class MyAsynTask extends AsyncTask<Void, Void, Boolean>{

        public MyAsynTask(){
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            SharedPreferences sp = getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
            String[] modes = sp.getString("mode", "").split("&");
            String[] times = sp.getString("time", "").split("&");
            String[] names = sp.getString("filename", "").split("&");
            HttpHelper helper = HttpHelper.getHttpHelper();
            data.clear();
            OkHttpClient client = new OkHttpClient();
            for(int i = 1; i < modes.length; i++){
                switch (modes[i]){
                    case "style":
                        url = URL_TRANSFER;
                        break;
                    case "soundconvert":
                        url = URL_SOUND;
                        break;
                    case "deepfake":
                        url = URL_FACE;
                        break;
                }
                Vedio vedio = new Vedio("");
                vedio.setMode(modes[i]);
                vedio.setName(names[i]);
                vedio.setTime(times[i]);
                Request request = new Request.Builder()
                        .url( url +  names[i].replace(".mp4", ""))
                        .build();
                Log.d("recent", url +  names[i].replace(".mp4", ""));
                try {
                    Response response = client.newCall(request).execute();
                    if(response.code() != 200){
                        vedio.setStatus("");
                        vedio.setPath("");
                        Log.d("aaaa", response.code() + " ");
                        data.add(vedio);
                        publishProgress();
                    }else{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        vedio.setStatus(jsonObject.getString("URL"));
                        vedio.setPath(jsonObject.getString("URL"));
                        data.add(vedio);
                        publishProgress();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            ra.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

        }
    }

}
