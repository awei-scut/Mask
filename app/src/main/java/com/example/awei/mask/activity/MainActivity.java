package com.example.awei.mask.activity;

import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.awei.mask.Frament.AboutFrag;
import com.example.awei.mask.Frament.FuncFrag;
import com.example.awei.mask.Frament.RecentFrag;
import com.example.awei.mask.R;
import com.example.awei.mask.Frament.VedioFrag;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import com.stephentuso.welcome.WelcomeHelper;


public class MainActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private VedioFrag vedioFrag;
    private AboutFrag aboutFrag;
    private FuncFrag funcFrag;
    private WelcomeHelper welcomeHelper;
    private RecentFrag recentFrag;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        welcomeHelper = new WelcomeHelper(this, WelcomActivity.class);
        welcomeHelper.show(savedInstanceState);
        welcomeHelper.forceShow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //toolbar
        toolbar.getMenu().clear();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.clear:
                        clearData();
                        break;
                }
                return true;
            }
        });

        // 底部导航栏处理
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                            ("精选", getResources().getColor(R.color.firstColor), R.drawable.ic_mic_black_24dp);
        bottomNavigationView.addTab(bottomNavigationItem1);
        BottomNavigationItem bottomNavigationItem2= new BottomNavigationItem
                ("功能", getResources().getColor(R.color.secondColor), R.drawable.ic_book_black_24dp);
        bottomNavigationView.addTab(bottomNavigationItem2);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                ("最近", getResources().getColor(R.color.thirdColor), R.drawable.ic_favorite_black_24dp);
        bottomNavigationView.addTab(bottomNavigationItem3);
        BottomNavigationItem bottomNavigationItem4 = new BottomNavigationItem
                ("关于", getResources().getColor(R.color.firstColor), R.drawable.github_circle);
        bottomNavigationView.addTab(bottomNavigationItem4);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Toolbar toolbar = findViewById(R.id.toolbar);
                switch (index){
                    case 0:
                        toolbar.getMenu().clear();
                        //设置颜色
                        toolbar.setBackgroundColor(getResources().getColor(R.color.firstColor));
                        //
                        if(vedioFrag == null){
                            vedioFrag = new VedioFrag();
                            fragmentTransaction.replace(R.id.frag, vedioFrag);
                        }else{
                            fragmentTransaction.replace(R.id.frag, vedioFrag);
                        }
                        break;
                    case 1:
                        toolbar.getMenu().clear();

                        toolbar.setBackgroundColor(getResources().getColor(R.color.secondColor));
                        if(funcFrag == null){
                            funcFrag = new FuncFrag();
                            fragmentTransaction.replace(R.id.frag, funcFrag);
                        }else{
                            fragmentTransaction.replace(R.id.frag, funcFrag);
                        }
                        break;
                    case 2:
                        toolbar.inflateMenu(R.menu.menu);
                        toolbar.setBackgroundColor(getResources().getColor(R.color.thirdColor));
                        if(recentFrag == null){
                            recentFrag = new RecentFrag();
                            fragmentTransaction.replace(R.id.frag, recentFrag);
                        }else{
                            Log.d("fragment", "aaa");
                            fragmentTransaction.replace(R.id.frag, recentFrag);
                        }
                        break;
                    case 3:
                        toolbar.getMenu().clear();
                        toolbar.setBackgroundColor(getResources().getColor(R.color.firstColor));
                        if(aboutFrag == null){
                            aboutFrag = new AboutFrag();
                            fragmentTransaction.replace(R.id.frag, aboutFrag);
                        }else{
                            fragmentTransaction.replace(R.id.frag, aboutFrag);
                        }
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }


    private void init(){
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottomNavigator);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //第一次启动show精选
        if(vedioFrag == null) {
            vedioFrag = new VedioFrag();
            fragmentTransaction.add(R.id.frag, vedioFrag);
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragmentTransaction.add(R.id.frag, vedioFrag).hide(fragment);
            }
            fragmentTransaction.show(vedioFrag);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        welcomeHelper.onSaveInstanceState(outState);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.clear:
//                clearData();
//                break;
//        }
//        return true;
//    }

    private void clearData(){
        SharedPreferences sp = getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
