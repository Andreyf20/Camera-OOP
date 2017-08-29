package com.andrey.instapoo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 24/08/17.
 */

public class Filter extends MainActivity {

    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_filter);
        preview = (ImageView)findViewById(R.id.preview);
    }

    public void Averaging(View view){
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        for (int i = 0; i < mutableBitmap.getWidth(); i++) {
            for (int j = 0; j < mutableBitmap.getHeight(); j++) {
                int pixel = mutableBitmap.getPixel(i, j);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int color = Color.argb(0xFF, (red+blue+green)/3, (red+blue+green)/3, (red+blue+green)/3);
                mutableBitmap.setPixel(i, j, color);
            }
        }
        preview.setImageBitmap(mutableBitmap);
    }

    public void Invert(View view){
        Bitmap finalimage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        int a,r,g,b;
        int pixelColor;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        for (int y = 0; y < height; y++){
            for (int x = 0; x< width; x++){
                pixelColor = bitmap.getPixel(x,y);
                a = Color.alpha(pixelColor);
                b = 255 - Color.blue(pixelColor);
                r = 255 - Color.red(pixelColor);
                g = 255 - Color.green(pixelColor);
                finalimage.setPixel(x,y,Color.argb(a,r,g,b));
            }
        }
        preview.setImageBitmap(finalimage);
    }
}
