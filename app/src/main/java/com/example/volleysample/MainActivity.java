package com.example.volleysample;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        Button xmlButton = (Button) findViewById(R.id.getXMLDoc);
        Button getImageButton = (Button) findViewById(R.id.getImageButton);

        imageView = (ImageView) (findViewById(R.id.imageView));

        xmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeVolleyRequest(context);
            }
        });

        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageView != null) {
                    loadImage(context, imageView);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeVolleyRequest(Context context) {
        //1. Get RequestQueue
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

        //2. Create Request
        String url = "http://www.pcworld.com/index.rss";
        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Parse XML Response
                        Log.e("makeVolleyRequest", "XML Response received.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("makeVolleyRequest", "Error: " + error.getMessage());
                    }
                }
        );

        //3. Add Request to queue Volley will execute it
        queue.add(request);

    }


    private void loadImage(Context context,final ImageView imageView) {
        String url = "http://www.gettyimages.ca/gi-resources/images/Homepage/Category-Creative/UK/UK_Creative_462809583.jpg";

        //1. Get ImageLoader
        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        //2. Define custom ImageListener on the ImageLoader
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                if(response.getBitmap() != null) {
                    imageView.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("loadImage", "Error: " + error.getMessage());
            }
        });
    }

    //Volley Provides NetworkImageView that encapsulates image loading task inside  NetworkImageView implementation.
    //Uncomment this code if you use Network ImageView
    //Just call setImageUrl on NetworkImageView object
//    private void loadImage(Context context, NetworkImageView imageView) {
//        String url = "http://www.gettyimages.ca/gi-resources/images/Homepage/Category-Creative/UK/UK_Creative_462809583.jpg";
//
//        //1. Get ImageLoader
//        ImageLoader loader = VolleySingleton.getInstance(context).getImageLoader();
//
//        //2. Set ImageUrl
//        imageView.setImageUrl(url, loader);
//    }
}
