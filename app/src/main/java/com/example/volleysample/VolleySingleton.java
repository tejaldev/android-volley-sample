package com.example.volleysample;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Class will provide an instance of RequestQueue and ImageLoader
 * Created by tejalpar on 3/30/16.
 */
public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static VolleySingleton getInstance(Context context) {
        if(instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    private VolleySingleton(Context context) {
        //1. initialize RequestQueue
        requestQueue = Volley.newRequestQueue(context);

        //2. initialize ImageLoader
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
