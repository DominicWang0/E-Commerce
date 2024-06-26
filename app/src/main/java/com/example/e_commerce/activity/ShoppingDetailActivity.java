package com.example.e_commerce.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;
import com.example.e_commerce.dbHelper.BagHelper;
import com.example.e_commerce.info.GoodsInfo;

public class ShoppingDetailActivity extends AppCompatActivity {

    ImageView iv_hero, iv_back;
    TextView tv_name, tv_price, tv_description;
    Button btn_buy;
    GoodsInfo goodsInfo = new GoodsInfo();
    LinearLayout ll_bag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        initView();
    }

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    private void initView() {
        iv_hero = findViewById(R.id.detail_iv_pic);
        tv_name = findViewById(R.id.detail_tv_name);
        tv_price = findViewById(R.id.detail_tv_price);
        tv_description = findViewById(R.id.detail_tv_description);
        btn_buy = findViewById(R.id.detail_btn_add_bag);

        View title = findViewById(R.id.detail_title);
        iv_back = title.findViewById(R.id.title_iv_back);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(v -> finish());

        ll_bag = title.findViewById(R.id.title_ll_bag);
        ll_bag.setVisibility(View.VISIBLE);
        ll_bag.setOnClickListener(v -> startActivity(new Intent(ShoppingDetailActivity.this, ShoppingBagActivity.class)));

        // 获取商品信息
        goodsInfo = (GoodsInfo) getIntent().getSerializableExtra("goods");
        assert goodsInfo != null;
        Log.i("Detail", goodsInfo.name);

        switch (goodsInfo.id) {
            case 1:
                iv_hero.setImageResource(R.drawable.iphone15prohero);
                break;
            case 2:
                iv_hero.setImageResource(R.drawable.iphone15hero);
                break;
            case 3:
                iv_hero.setImageResource(R.drawable.iphone14hero);
                break;
            case 4:
                iv_hero.setImageResource(R.drawable.iphone13hero);
                break;
            case 5:
                iv_hero.setImageResource(R.drawable.iphonese);
                break;
        }
        tv_name.setText("Buy " + goodsInfo.name);
        tv_price.setText("$" + goodsInfo.price);
        tv_description.setText(goodsInfo.description);

        // 设置按钮功能
        btn_buy.setOnClickListener(v -> {
            // 添加到购物车
            Log.i("Detail", "Add to bag");
            if (insert(goodsInfo.id) == -1) {
                Log.i("Detail", "Add to bag failed");
                Toast.makeText(ShoppingDetailActivity.this, "Add to Bag Failed", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("Detail", "Add to bag successfully");
                Toast.makeText(ShoppingDetailActivity.this, "Add to Bag Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public long insert(int data) {
        //获取数据库对象
        BagHelper myHelper = new BagHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();

        //数据源
        ContentValues values = new ContentValues();

        values.put("item_id", data);

        //存入
        long count = db.insert("bag", null, values);
        Log.i("Detail", "Insert " + count);
        db.close();
        return count;
    }
}