package com.example.myapplication_week1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

public class ImageClicked extends AppCompatActivity {
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;
    Button delImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_clicked_layout);

        final Intent intent = getIntent();

        mImageView = (ImageView)findViewById(R.id.image);

        Bitmap img = StringToBitMap(intent.getStringExtra("image"));
        mImageView.setImageBitmap(img);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        delImg = (Button)findViewById(R.id.delImg);
        delImg.setVisibility(View.VISIBLE);
        delImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity mainAct = new MainActivity();
                MainActivity.HttpDelAsyncTask httpTask = new MainActivity.HttpDelAsyncTask(mainAct);
                httpTask.execute("http://192.249.19.243:9780/api/phonebooks/name/image"+intent.getStringExtra("ct")+intent.getStringExtra("id"));
                int del = Integer.parseInt(intent.getStringExtra("ct"));
//                for(int i=del+1; i<=MainActivity.imageAdapter.getCount()-1;i++){
//                    MainActivity.HttpUpdateAsyncTask httpTask2 = new MainActivity.HttpUpdateAsyncTask(mainAct);
//                    httpTask2.execute("http://192.249.19.243:9780/api/phonebooks/name/image"+Integer.toString(i), "image"+Integer.toString((i-1)), BitMapToString(MainActivity.ImageAdapter.getItem(i-1).getImage()), "-1");
//                }
                Intent restart = new Intent(getApplicationContext(), MainActivity.class);
                restart.putExtra("id",intent.getStringExtra("id"));
                delImg.setVisibility(View.GONE);
                startActivity(restart);
            }
        });

    }
    public String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *=scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor,10.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}
