package com.andrey.instapoo;

import static java.lang.Math.max;
import static java.lang.Math.min;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by andrey on 24/08/17.
 */

public class Filter extends MainActivity {

    protected ImageView preview;
    protected Bitmap bitmapcopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_filter);
        preview = (ImageView)findViewById(R.id.preview);
    }

    public Bitmap getCopy(){
        bitmapcopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        if(bitmap.getHeight() > 720 || bitmap.getWidth() > 1290){
            Matrix m = new Matrix();
            float reqWidth = 1290;
            float reqHeight = 720;
            m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
            bitmapcopy = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        }
        return bitmapcopy;
    }

    public void averaging(View view){
        bitmapcopy = getCopy();

        for (int i = 0; i < bitmapcopy.getWidth(); i++) {
            for (int j = 0; j < bitmapcopy.getHeight(); j++) {
                int pixel = bitmapcopy.getPixel(i, j);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int color = Color.argb(0xFF, (red+blue+green)/3, (red+blue+green)/3, (red+blue+green)/3);
                bitmapcopy.setPixel(i, j, color);
            }
        }
        preview.setImageBitmap(bitmapcopy);
    }

    public void invert(View view){
        bitmapcopy = getCopy();

        int a,r,g,b;
        int pixelColor;
        int height = bitmapcopy.getHeight();
        int width = bitmapcopy.getWidth();

        for (int y = 0; y < height; y++){
            for (int x = 0; x< width; x++){
                pixelColor = bitmapcopy.getPixel(x,y);
                a = Color.alpha(pixelColor);
                b = 255 - Color.blue(pixelColor);
                r = 255 - Color.red(pixelColor);
                g = 255 - Color.green(pixelColor);
                bitmapcopy.setPixel(x,y,Color.argb(a,r,g,b));
            }
        }
        preview.setImageBitmap(bitmapcopy);
    }

    public void desaturation(View view){
        bitmapcopy = getCopy();

        int a,r,g,b,D;
        int pixelColor;
        int height = bitmapcopy.getHeight();
        int width = bitmapcopy.getWidth();

        for (int y = 0; y < height; y++){
            for (int x = 0; x< width; x++){
                pixelColor = bitmap.getPixel(x,y);
                a = Color.alpha(pixelColor);
                b = Color.blue(pixelColor);
                r = Color.red(pixelColor);
                g = Color.green(pixelColor);
                D = min(min(r,g),b)+max(max(r,g),b);
                bitmapcopy.setPixel(x,y,Color.argb(a,D,D,D));

            }
        }
        preview.setImageBitmap(bitmapcopy);
    }
}
