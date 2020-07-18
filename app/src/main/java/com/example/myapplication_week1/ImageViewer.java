package com.example.myapplication_week1;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ImageViewer extends LinearLayout {
    ImageView image;
    public ImageViewer(Context context){
        super(context);
        init(context);
    }

    public ImageViewer(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.imageitem,this,true);
        image = (ImageView)findViewById(R.id.image);
    }

    public void setImage(ImageItem imageItem){
        image.setImageResource(imageItem.getImage());
    }
}
