package com.jw.jwutils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ZhaoJiangWei on 2016/7/27.
 * BitmapUtils,实现三层缓存
 */
public class BitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public BitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    /**
     * 展示图片
     *
     * @param img
     * @param url
     */

    public void disPlay(ImageView img, String url, int imgDefault) {
        img.setImageResource(imgDefault);
        Bitmap bitmap;
        //内存缓存
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            img.setImageBitmap(bitmap);
            System.out.println("从内存获取图片啦.....");
            return;
        } else {
            System.out.println("内存缓存空.....");
        }

        //本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            img.setImageBitmap(bitmap);
            System.out.println("从本地获取图片啦.....");
            //从本地获取图片后,保存至内存中
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            return;
        } else {
            System.out.println("本地缓存空.....");
        }
        //网络缓存
        mNetCacheUtils.getBitmapFromNet(img, url);
    }

    /**
     * 清除本地所有缓存
     */
    public void clearCache() {
        mMemoryCacheUtils.clearMemoryCache();
        mLocalCacheUtils.clearLocalCache();

    }
}

