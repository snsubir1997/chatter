package com.example.subir.recyclerviewdemo;

public class ItemData {
    String name, email, no;
    int url;

    public ItemData(String name, String email, String no, int url) {
        this.name = name;
        this.email = email;
        this.no = no;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
