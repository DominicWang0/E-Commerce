package com.example.e_commerce;

import static com.example.e_commerce.utils.CopyDBUtils.copyDBFile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.dbHelper.GoodsHelper;
import com.example.e_commerce.info.GoodsInfo;

import java.io.IOException;
import java.util.ArrayList;

public class ShoppingChannelActivity extends AppCompatActivity {

    GridLayout goodsGridLayout;
    ArrayList<GoodsInfo> goodsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            copyDBFile("ECommerce.db", this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setContentView(R.layout.activity_shopping_channel);
        init();
    }

    void init() {
        getGoodsData();
        initChannel();
    }

    void initChannel() {
        ImageView hero;
        TextView name, price;
        goodsGridLayout = findViewById(R.id.channel_gl_list);


        for (GoodsInfo goodsList : goodsList) {
            View channelView = LayoutInflater.from(this).inflate(R.layout.item_goods, null, false);

            // 获取控件
            hero = channelView.findViewById(R.id.item_iv_thumb);
            name = channelView.findViewById(R.id.item_tv_name);
            price = channelView.findViewById(R.id.item_tv_price);

            // 设置布局参数
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setGravity(Gravity.CENTER);
            params.leftMargin = 70;
            params.rightMargin = 70;

            // 设置控件内容
            switch (goodsList.id) {
                case 1:
                    hero.setImageResource(R.drawable.iphone15prohero);
                    break;
                case 2:
                    hero.setImageResource(R.drawable.iphone15hero);
                    break;
                case 3:
                    hero.setImageResource(R.drawable.iphone14hero);
                    break;
                case 4:
                    hero.setImageResource(R.drawable.iphone13hero);
                    break;
                case 5:
                    hero.setImageResource(R.drawable.iphonese);
                    break;
            }
            name.setText(goodsList.name);
            price.setText("$" + String.valueOf(goodsList.price));

            channelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ShoppingChannel", "点击了商品：" + goodsList.name);
                    Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
                    intent.putExtra("goods", goodsList);
                    startActivity(intent);
                }
            });


            // 添加到GridLayout中
            goodsGridLayout.addView(channelView, params);
        }
    }

    @SuppressLint("Range")
    private void getGoodsData() {
        GoodsHelper goodsHelper = new GoodsHelper(this);
        SQLiteDatabase goodsDB = goodsHelper.getReadableDatabase();

        // 获取商品数据
        Cursor goodsData = goodsDB.query("goods", new String[]{"id", "name", "price", "description", "pic_path"}, null, null, null, null, null);
        Log.i("ShoppingChannel", "获取商品数：：" + goodsData.getCount());

        // 遍历购物袋数据
        while (goodsData.moveToNext()) {
            Log.i("ShoppingChannel", "商品id：" + String.valueOf(goodsData.getInt(goodsData.getColumnIndex("id"))));
            Log.i("ShoppingChannel", "商品name：" + goodsData.getString(goodsData.getColumnIndex("name")));
            Log.i("ShoppingChannel", "商品price：" + String.valueOf(goodsData.getFloat(goodsData.getColumnIndex("price"))));
            Log.i("ShoppingChannel", "商品description：" + goodsData.getString(goodsData.getColumnIndex("description")));
            Log.i("ShoppingChannel", "商品pic_path：" + goodsData.getString(goodsData.getColumnIndex("pic_path")));

            GoodsInfo goodsInfo = new GoodsInfo(goodsData.getInt(goodsData.getColumnIndex("id")), goodsData.getString(goodsData.getColumnIndex("name")), goodsData.getFloat(goodsData.getColumnIndex("price")), goodsData.getString(goodsData.getColumnIndex("description")), goodsData.getString(goodsData.getColumnIndex("pic_path")));

            goodsList.add(goodsInfo);
        }
    }


}