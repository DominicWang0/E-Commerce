package com.example.e_commerce.info;

public class BagInfo {
    public int goodsId;
    public GoodsInfo goods;

    public BagInfo(int goodsId, GoodsInfo goods) {
        this.goodsId = goodsId;
        this.goods = goods;
    }
}
