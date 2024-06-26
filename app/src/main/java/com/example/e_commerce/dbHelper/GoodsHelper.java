package com.example.e_commerce.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GoodsHelper extends SQLiteOpenHelper {
    public GoodsHelper(@Nullable Context context) {
        super(context, "ECommerce.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE goods (id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),price FLOAT,description VARCHAR(100),pic_path varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
