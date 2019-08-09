package com.example.awei.mask.activity;

import com.example.awei.mask.R;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class WelcomActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.red_background)
                .page(new TitlePage(R.drawable.ic_thumb_up_white,
                        "Welcome to Mask").background(R.color.red_background)
                ).swipeToDismiss(false)
                .build();
    }
}
