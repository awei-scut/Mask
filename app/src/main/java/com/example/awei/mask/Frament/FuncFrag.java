package com.example.awei.mask.Frament;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.awei.mask.R;
import com.example.awei.mask.utils.GetPathFromUri;
import com.example.awei.mask.utils.HttpHelper;
import com.github.rubensousa.raiflatbutton.RaiflatButton;
import com.youth.picker.PickerView;
import com.youth.picker.entity.PickerData;
import com.youth.picker.listener.OnPickerClickListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;


public class FuncFrag extends Fragment {
    private final static String[] data1 = {"candy","composition_vii","feathers",
            "la_muse","mosaic","starry_night","the_scream","the_wave","udnie"};
    private final static String[] data2 = {"刘昊然","迪丽热巴","胡歌","卷福","刘亦菲","宋茜"
            ,"田柾国","王凯","吴彦祖","张敏","张卫健","朱茵"};
    private final static String[] data2_ = {"lhr","dlrb","hg","jf","lyf","sq"
            ,"tjg","wk","wyz","zm","zwj","zy"};
    private final static String[] data3 = {"男声变女声", "女声变男声"};
    private final static String[] name = {"请选择风格" ,"请选择替换人物", "请选择"};

    private String vedioPath;
    private String mode;
    private String model;
    private RaiflatButton raiflatButton1;
    private RaiflatButton raiflatButton2;
    private RaiflatButton raiflatButton3;

    private AlertDialog alertDialog;

    private int choose = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //上传视频失败
                    alertDialog.hide();
                    showDialog(getContext(), 0);
                    break;
                case 1:
                    alertDialog.hide();
                    showDialog(getContext(), 1);
                    saveData();
                    break;
            }
        }
    };

    private void saveData(){
        SharedPreferences sp = getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String time = month + "-" + day + " " + hour + ":" + min;
        Log.e("time", time);
        editor.putString("time", sp.getString("time", "") + "&"+time);
        editor.putString("mode", sp.getString("mode", "") + "&"+mode);
        String[] split = vedioPath.split("/");
        String filename = split[split.length-1];
        editor.putString("filename", sp.getString("filename","") + "&" +filename);
        editor.commit();
    }


    private void showDialog(Context context, int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        if(i == 0){
            builder.setMessage("视频上传失败，请重试");
        }else {
            builder.setMessage("视频上传成功，请于'最近'中查看结果");
        }
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.func_choose, container, false);
        raiflatButton1 = view.findViewById(R.id.transfer);
        raiflatButton2 = view.findViewById(R.id.changeface);
        raiflatButton3 = view.findViewById(R.id.changevoice);
        raiflatButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = 0;
                mode = "style";
                requestPer();
            }
        });

        raiflatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = 1;
                mode = "deepfake";
                requestPer();
            }
        });

        raiflatButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = 2;
                mode = "soundconvert";
                requestPer();
            }
        });
        return view;
    }
//
    private void requestPer(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1)
            ;
            openBlum();
        }else{
            openBlum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openBlum();
                }else{
                    Toast.makeText(getContext(), "你拒绝了权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openBlum(){
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("video/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
        }
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                Log.e("aaa", resultCode + "");
                if(resultCode == -1 ){
                    Uri uri = data.getData();
                    vedioPath = GetPathFromUri.getPath(getContext(), uri);
                    Log.e("-----", "path: " + vedioPath);
                    switch (choose){
                        case 0:
                            showPicker(data1, name[0], 1200);
                            break;
                        case 1:
                            showPicker(data2, name[1], 1200);
                            break;
                        case 2:
                            showPicker(data3, name[2], 800);
                            break;
                    }
                }
                break;
        }
    }



    private void upLoad(){
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage("正在上传");
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        HttpHelper helper = HttpHelper.getHttpHelper();
        for(int i = 0; i < data2.length; i++){
            if(data2[i].equals(model)){
                model = data2_[i];
            }
        }
        helper.put(model, vedioPath, mode,new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("----", "shibai");
                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e("---", response.body().string());
                Message message = Message.obtain();
                if(response.code() == 200){
                    message.what = 1;
                    handler.sendMessage(message);
                }else{
                    message.what = 0;
                    handler.sendMessage(message);
                }
            }
        });
    }


    private void showPicker(String[] data, String name, int height){
        PickerData pickerData = new PickerData();
        pickerData.setFirstDatas(data);
        pickerData.setHeight(height);
        pickerData.setPickerTitleName(name);
        PickerView view = new PickerView((Activity) getContext(), pickerData);
        view.show(raiflatButton1);
        view.setOnPickerClickListener(new OnPickerClickListener() {
            @Override
            public void OnPickerClick(PickerData pickerData) {
            }
            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {
                model = pickerData.getFirstText();
                Log.e("----", mode);
                upLoad();
            }
        });
    }


}
