package com.andrey.instapoo;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andrey on 24/08/17.
 */

public class Filter extends MainActivity {

    protected ImageView preview;
    protected Bitmap bitmapcopy;
    String mCameraFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_filter);
        preview = (ImageView)findViewById(R.id.preview);
        preview.setImageBitmap(bitmap);
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
                pixelColor = bitmapcopy.getPixel(x,y);
                a = Color.alpha(pixelColor);
                b = Color.blue(pixelColor);
                r = Color.red(pixelColor);
                g = Color.green(pixelColor);
                D = (max(max(b, r), g) + min(min(b, r), g)) / 2;
                bitmapcopy.setPixel(x,y,Color.argb(a,D,D,D));
            }
        }
        preview.setImageBitmap(bitmapcopy);
    }

    public void decompostitionmax(View view) {
        bitmapcopy = getCopy();

        int a, r, g, b, D;
        int pixelColor;
        int height = bitmapcopy.getHeight();
        int width = bitmapcopy.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelColor = bitmapcopy.getPixel(x, y);
                a = Color.alpha(pixelColor);
                b = Color.blue(pixelColor);
                r = Color.red(pixelColor);
                g = Color.green(pixelColor);
                D = max(max(r, g), b);
                bitmapcopy.setPixel(x, y, Color.argb(a, D, D, D));
            }
        }
        preview.setImageBitmap(bitmapcopy);
    }

    public void decompositionmin (View view) {
        bitmapcopy = getCopy();

        int a,r,g,b,D;
        int pixelColor;
        int height = bitmapcopy.getHeight();
        int width = bitmapcopy.getWidth();

        for (int y = 0; y < height; y++){
            for (int x = 0; x< width; x++){
                pixelColor = bitmapcopy.getPixel(x,y);
                a = Color.alpha(pixelColor);
                b = Color.blue(pixelColor);
                r = Color.red(pixelColor);
                g = Color.green(pixelColor);
                D = min(min(r, g), b);
                bitmapcopy.setPixel(x,y,Color.argb(a,D,D,D));
            }
        }
        preview.setImageBitmap(bitmapcopy);
    }

    public void filtrogaus(View view){
]
        Mat mat = inputFrame.gray();
        org.opencv.core.Size s = new Size(3,3);
        Imgproc.GaussianBlur(mat, mat, s, 2);
        return mat;
        }
        Matrix m = new Matrix();
        float reqWidth = bitmapcopy.getWidth();
        float reqHeight = bitmapcopy.getHeight();
        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
        bitmapcopy = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);


    }

    public void saveIMG(View view) {
        String df = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fname = "JPEG_"+ df + ".jpg";

        File myDir = new File(Environment.getExternalStorageDirectory() + "/InstaPOO/");
        myDir.mkdirs();

        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmapcopy.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            super.galleryAddPic();
            saveIMGdiagBox();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveIMGdiagBox(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Image Saved!")
                .setMessage("The image has been saved")
                .setPositiveButton(android.R.string.ok , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Does something if yes
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })*/
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
