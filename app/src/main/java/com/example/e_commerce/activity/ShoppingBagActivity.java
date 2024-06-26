package com.example.e_commerce.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;
import com.example.e_commerce.dbHelper.BagHelper;
import com.example.e_commerce.dbHelper.GoodsHelper;
import com.example.e_commerce.info.BagInfo;
import com.example.e_commerce.info.GoodsInfo;

import java.util.ArrayList;

public class ShoppingBagActivity extends AppCompatActivity {
    ArrayList<BagInfo> bagList = new ArrayList<>();
    ArrayList<GoodsInfo> goodsList = new ArrayList<>();
    GridLayout gl_bag;
    ImageView iv_back;
    TextView tv_count, tv_total;
    Button btn_cleanAll, btn_checkout;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        init();
    }


    private void init() {

        gl_bag = findViewById(R.id.bag_gl_list);
        tv_count = findViewById(R.id.bag_tv_count);
        tv_total = findViewById(R.id.bag_tv_total);
        btn_cleanAll = findViewById(R.id.bag_btn_cleanall);
        btn_checkout = findViewById(R.id.bag_btn_checkout);


        getGoodsData();
        getBagData();
        showData();
        initTitle();

        if (bagList.isEmpty()) {
            btn_checkout.setBackground(getDrawable(R.color.gray));
        }

        btn_cleanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = new BagHelper(ShoppingBagActivity.this).getWritableDatabase();
                db.execSQL("delete from bag");
                db.close();
                bagList.clear();
                gl_bag.removeAllViews();
                total = 0;
                showData();
                Toast.makeText(ShoppingBagActivity.this, "Clean All Successful", Toast.LENGTH_SHORT).show();
                btn_checkout.setBackground(getDrawable(R.color.gray));
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total == 0) {
                    Toast.makeText(ShoppingBagActivity.this, "Please select some goods", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(ShoppingBagActivity.this, "Checkout Scuessful", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void initTitle() {
        View title = findViewById(R.id.bag_title);
        iv_back = title.findViewById(R.id.title_iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @SuppressLint("Range")
    private void getGoodsData() {
        GoodsHelper goodsHelper = new GoodsHelper(this);
        SQLiteDatabase goodsDB = goodsHelper.getReadableDatabase();

        // 获取商品数据
        Cursor goodsData = goodsDB.query("goods", new String[]{"id", "name", "price", "description", "pic_path"}, null, null, null, null, null);
        Log.i("ShoppingBag", "获取商品数：：" + goodsData.getCount());

        // 遍历购物袋数据
        while (goodsData.moveToNext()) {
            Log.i("ShoppingBag", "商品id：" + String.valueOf(goodsData.getInt(goodsData.getColumnIndex("id"))));
            Log.i("ShoppingBag", "商品name：" + goodsData.getString(goodsData.getColumnIndex("name")));
            Log.i("ShoppingBag", "商品price：" + String.valueOf(goodsData.getFloat(goodsData.getColumnIndex("price"))));
            Log.i("ShoppingBag", "商品description：" + goodsData.getString(goodsData.getColumnIndex("description")));
            Log.i("ShoppingBag", "商品pic_path：" + goodsData.getString(goodsData.getColumnIndex("pic_path")));

            GoodsInfo goodsInfo = new GoodsInfo(goodsData.getInt(goodsData.getColumnIndex("id")), goodsData.getString(goodsData.getColumnIndex("name")), goodsData.getFloat(goodsData.getColumnIndex("price")), goodsData.getString(goodsData.getColumnIndex("description")), goodsData.getString(goodsData.getColumnIndex("pic_path")));

            goodsList.add(goodsInfo);
        }
    }

    @SuppressLint("Range")
    private void getBagData() {
        BagHelper bagHelper = new BagHelper(this);
        SQLiteDatabase bagDB = bagHelper.getReadableDatabase();

        // 获取购物袋数据
        Cursor bagData = bagDB.query("bag", new String[]{"item_id"}, null, null, null, null, null);

        if (bagData.getCount() == 0) {
            Log.i("ShoppingBag", "购物袋为空");
            Toast.makeText(this, "Your Bag is Empty", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("ShoppingBag", "购物袋不为空");
            bagList.clear();

            // 遍历购物袋数据
            while (bagData.moveToNext()) {
                Log.i("ShoppingBag", "购物袋id：" + String.valueOf(bagData.getInt(bagData.getColumnIndex("item_id"))));
                // 根据bag的商品id查找商品信息
                for (GoodsInfo goodsInfo : goodsList) {
                    if (goodsInfo.id == bagData.getInt(bagData.getColumnIndex("item_id"))) {
                        BagInfo bagItem = new BagInfo(bagData.getInt(bagData.getColumnIndex("item_id")), goodsInfo);
                        bagList.add(bagItem);
                        break;
                    }
                }
            }
        }
    }


    private void showData() {
        ImageView hero;
        TextView name, price;

        Log.i("ShoppingBag", "bagList.size(): " + bagList.size());

        for (BagInfo bagList : bagList) {
            View bagItemView = LayoutInflater.from(this).inflate(R.layout.item_bag, null, false);
            Log.i("ShoppingBag", "1");

            // 创建一个控件
            hero = bagItemView.findViewById(R.id.item_iv_thumb);
            name = bagItemView.findViewById(R.id.item_tv_name);
            price = bagItemView.findViewById(R.id.item_tv_price);
            // 设置控件的属性
            switch (bagList.goods.id) {
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
            name.setText(bagList.goods.name);
            price.setText("$" + String.valueOf(bagList.goods.price));
            total += bagList.goods.price;

            // 创建并设置布局参数
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.MATCH_PARENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(0, 0, 0, 20); // 设置底部间距以分隔项目
            params.setGravity(Gravity.CENTER);

            // 添加到GridLayout中
            gl_bag.addView(bagItemView, params);
        }

        tv_count.setText(String.valueOf(bagList.size()));
        tv_total.setText("$" + String.valueOf(total));

    }


}