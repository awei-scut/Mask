package com.example.awei.mask.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.net.BindException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * okhttp3帮助类
 */


public class HttpHelper {

    private final static String BASE_URL_face = "http://dianshemask3.natapp1.cc";
    private final static String BASE_URL_transfer = "http://dianshemask4.natapp1.cc";
    private final static String BASE_URL_sound = "http://dianshemask4.natapp1.cc";


    /**
     * get 异步请求获取url
     * @param filename
     * @param callback
     */
//    public void getUrl(String filename, Callback callback){
//        String url = BASE_URL_transfer + "/file/" + filename.replace(".mp4", "");
//        doGet(url, callback);
//    }

    /**
     * 上传视频至服务器
     *
     */
    public void put(String model,String path, String mode,Callback callback){
        File file = new File(path);
        String url = null;
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("vedio/*"), file);
        requestBody.addFormDataPart("file", file.getName(), body);
        requestBody.addFormDataPart("model", model);
        requestBody.addFormDataPart("filename", file.getName().replace(".mp4", ""));
        requestBody.addFormDataPart("mode", mode);
        switch (mode){
            case "style":
                url = BASE_URL_transfer;
                break;
            case "soundconvert":
                url = BASE_URL_sound;
                break;
            case "deepfake":
                url = BASE_URL_face;
                break;
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        client.newCall(request).enqueue(callback);
    }




    /***********Canned And Unchangeable************************************/
    private static HttpHelper httpHelper;
    private final OkHttpClient client;

    private HttpHelper() {

        client = new OkHttpClient();
    }

    public static HttpHelper getHttpHelper() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    /**
     * 带参数的GET请求
     * @param params
     * @param url
     * @param callback
     */
    private void doGet(LinkedHashMap<String, String> params, String url, Callback callback) {
        String URL = attachHttpGetParams(url, params);
        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Get
     * @param url
     * @param callback
     */
    public void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Put
     * @param url
     * @param callback
     */
    public void doPut(String url,Map<String,String > params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .header("content-type","application/json")

                .addHeader("user-agent", "android")
                .put(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 带cookie的Get请求
     * @param url
     * @param callback
     */
    private void cookieGet(String url,Map<String, String> params, Callback callback) {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * post
     * @param url
     * @param params
     * @param callback
     */
    private void doPost(String url, Map<String, String> params, Callback callback) {
        // 创建Request
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 带cookie的delete请求
     * @param url
     * @param callback
     * @param cookie
     */
    private void cookieDelete(String url, Callback callback,String cookie) {
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .delete()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 带cookie的post请求
     * @param url
     * @param params
     * @param callback
     * @param cookie
     */
    private void cookiePost(String url, Map<String, String> params, Callback callback,String cookie) {
        // 创建Request
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);

    }


    private void doPostCookieJson(String cookie,String url, Map<String, String> params, Callback callback) {
        Gson mGson =new Gson();
        String param = mGson.toJson(params);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .post(body)
                .url(url)
                .header("Cookie", cookie)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post
     * @param url
     * @param params
     * @param callback
     */
    private void doPostJson(String url, Map<String, String> params, Callback callback) {
        Gson mGson =new Gson();
        String param = mGson.toJson(params);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, param);
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .post(body).url(url).build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 带cookie的patch请求
     * @param url
     * @param params
     * @param callback
     * @param cookie
     */
    private void doPatch(String url, Map<String, Object> params, Callback callback,String cookie) {
        // 创建Request
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .patch(formBody)
                .build();
        client.newCall(request).enqueue(callback);

    }


    /**
     * 为HttpGet 的 url方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    private static String attachHttpGetParams(String url, LinkedHashMap<String, String> params) {

        Iterator<String> keys = params.keySet().iterator();
        Iterator<String> values = params.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = null;
            try {
                value = URLEncoder.encode(values.next(), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            stringBuffer.append(keys.next() + "=" + value);
            if (i != params.size() - 1) {
                stringBuffer.append("&");
            }
            Log.e("stringBuffer", stringBuffer.toString());
        }

        return url + stringBuffer.toString();
    }

    /**
     * 文件上传
     * @param file
     * @param callback
     * @param url   采用put方式
     * */
//    public void uploadFile(File file, Callback callback,String url) {
//        // 网络操作
//        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("avatarImage", file.getName(),
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
//        //创建RequestBody
//        RequestBody requestBody = requestBodyBuilder.build();
//        //创建request
//        Request request = new Request.Builder()
//                .header("Cookie", SaveCookieHelper.getCookie())
//                .url(url)
//                .post(requestBody)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
}
