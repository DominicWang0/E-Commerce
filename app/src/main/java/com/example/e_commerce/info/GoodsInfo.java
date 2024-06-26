package com.example.e_commerce.info;

import java.io.Serializable;

public class GoodsInfo implements Serializable {
    public int id;
    public String name;
    public float price;
    public String description;
    public String picPath;

    public GoodsInfo(int id, String name, float price, String description, String picPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.picPath = picPath;
    }

    public GoodsInfo() {
    }
}
