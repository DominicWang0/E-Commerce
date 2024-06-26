package com.example.e_commerce.dbHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BagHelper extends SQLiteOpenHelper {
    public BagHelper(@Nullable Context context) {
        super(context, "ECommerce.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE bag (id INTEGER PRIMARY KEY AUTOINCREMENT,item_id INTEGER,count INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
