package com.example.e_commerce.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class CopyDBUtils {

    private static InputStream is;
    private static FileOutputStream fos;

    public static void copyDBFile(Context context, String dbName) {

        //databases文件夹目录
        @SuppressLint("SdCardPath") String path = "/data/data/" + context.getPackageName() + "/databases";

        //后面通过SQLiteDatabase.openDatabas获取数据库
        File file = new File(path + "/" + dbName);
        try {
            //创建文件夹（如果之前没有创建过数据库，data/data/包名/下的databases目录不存在）
            File dir = new File(path);
            if (!dir.exists()) {
                Log.i("CopyDBUtils", "Database dir is not exists.");
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }
            /*
             * mkdir() 与 mkdirs() 的区别
             * mkdirs()可以创建多级目录文件
             * 比如要在 C:\a\b\ 目录下创建一个文件test.txt  (此时不存在文件夹a和b)
             *  mkdirs()就会先创建文件夹a，文件夹b。最后创建test.txt问价
             * 但是mkdir()则创建失败,返回创建结果false
             * */
            if (file.exists()) {
                Log.i("CopyDBUtils", "Database file is exists.");
                return;
//              删除已经存在的
//              file.deleteOnExit();
            }
            //创建新的文件
            if (!file.exists())
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            // 从assets目录下复制
            is = context.getAssets().open(dbName + ".db");
            fos = new FileOutputStream(file);
            int len;
            byte[] b = new byte[1024];
            while ((len = is.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e1) {
                //noinspection CallToPrintStackTrace
                e1.printStackTrace();
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void copyDBFile(String databaseName, Context context) throws IOException {
        File dbPath = context.getDatabasePath(databaseName);

        // 检查数据库文件是否已经存在
        if (!dbPath.exists()) {
            // 创建数据库目录
            //noinspection ResultOfMethodCallIgnored
            Objects.requireNonNull(dbPath.getParentFile()).mkdirs();

            // 从 assets 复制数据库文件
            InputStream inputStream = context.getAssets().open(databaseName);
            OutputStream outputStream = Files.newOutputStream(dbPath.toPath());

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }


}

