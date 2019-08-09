package com.example.awei.mask.Frament;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.awei.mask.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");
        String description = "电子系统综合设计-结合深度学习技术的短视频APP \n Designed by wzw、ljl、yhy、jwq、wym";
        View aboutPage = new AboutPage(getContext())
                .setDescription(description)
                .isRTL(false)
                .setImage(R.mipmap.avator)
                .addItem(versionElement)
                .addEmail("312021332@qq.com")
                .addGitHub("https://awei-scut.github.io/")
                .create();
        return aboutPage;
    }
}
