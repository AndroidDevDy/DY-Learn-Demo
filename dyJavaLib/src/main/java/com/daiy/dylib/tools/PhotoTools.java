package com.daiy.dylib.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class PhotoTools {

    /**
     * 图片文件过大时，压缩图片，使图片不超过1M
     *
     * @param photo_url
     * @return
     */
    public static Bitmap resizePhoto(String photo_url) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //不会加载，只会获取图片的一个尺寸
        //options里面储存了图片的高度和宽度
        //读取文件
        BitmapFactory.decodeFile(photo_url, options);
        //改变图片的大小
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024);
        options.inSampleSize = (int) Math.ceil(ratio);
        //设置后会加载图片
        options.inJustDecodeBounds = false;
        //图片压缩完成
        return BitmapFactory.decodeFile(photo_url, options);
    }

    /**
     * 将bitmap转换成byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
