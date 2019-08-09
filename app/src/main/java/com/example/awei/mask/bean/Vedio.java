package com.example.awei.mask.bean;

public class Vedio {

    private String path;
    private String time;
    private String title;
    private String mode;
    private String status;
    private String name;

    public Vedio(String status){
        this.status = status;
    }

    public Vedio(String path, String time, String title){
        this.path = path;
        this.time = time;
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }
    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
