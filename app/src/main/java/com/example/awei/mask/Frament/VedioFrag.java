package com.example.awei.mask.Frament;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.awei.mask.R;
import com.example.awei.mask.bean.Vedio;
import com.example.awei.mask.adapter.vedioAdapter;

import java.util.ArrayList;
import java.util.List;

public class VedioFrag extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<Vedio> list = new ArrayList<>();
        String path1 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/1d7083515285890790090692265/AZlvV5aj6UUA.mp4";
        String path2 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/1d6249555285890790090689416/ovX3xWUgpXoA.mp4" ;
        String path3 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/1d14eb285285890790090640119/AiPwEYJoihMA.mp4";
        String path4 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/1d5e83935285890790090682808/hEIzXSaJabwA.mp4";

        String path5 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/23dea8ae5285890790090911178/D0BBerA9crUA.mp4";

        String path6 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/67dfe30e5285890790091563480/wX7RcGA08HkA.mp4";
        String path7 = "http://1256003481.vod2.myqcloud.com/7a4a69e0vodcq1256003481/67dfd5e85285890790091563150/PuohlyWtAH4A.mp4";


        list.add(new Vedio(path1, "2019/4/16", "mosaic风格"));
        list.add(new Vedio(path2, "2019/4/17", "udnie风格"));
        list.add(new Vedio(path3, "2019/4/18", "la_muse风格"));
        list.add(new Vedio(path4, "2019/4/19", "feathers风格"));
        list.add(new Vedio(path5, "2019/4/20", "万淇->胡歌"));
        list.add(new Vedio(path6, "2019/4/21", "女声变男声"));
        list.add(new Vedio(path7, "2019/4/21", "男声变女声"));

        View view = inflater.inflate(R.layout.vedio_layout, container, false);
        ListView lv = view.findViewById(R.id.listview);
        vedioAdapter adapter = new vedioAdapter(getActivity(), list);
        lv.setDividerHeight(0);
        lv.setAdapter(adapter);
        return view;
    }
}
