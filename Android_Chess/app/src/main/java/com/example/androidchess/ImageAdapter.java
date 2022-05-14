package com.example.androidchess;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.androidchess.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public boolean isEnabled(int position) {
            return true;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
           // imageView.setScaleType(ImageView.ScaleType.FIT_START);
             imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    public void changeImage(int position, int ImgId){
        mThumbIds[position] =ImgId;
        notifyDataSetChanged();
    }
    public Integer getImgID(int position){
        return mThumbIds[position];
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.black_rook,R.drawable.black_knight,
            R.drawable.black_bishop,R.drawable.black_queen,
            R.drawable.black_king,R.drawable.black_bishop,
            R.drawable.black_knight,R.drawable.black_rook,
            R.drawable.black_pawn,R.drawable.black_pawn,
            R.drawable.black_pawn,R.drawable.black_pawn,
            R.drawable.black_pawn,R.drawable.black_pawn,
            R.drawable.black_pawn,R.drawable.black_pawn,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.blank_square,R.drawable.blank_square,
            R.drawable.white_pawn,R.drawable.white_pawn,
            R.drawable.white_pawn,R.drawable.white_pawn,
            R.drawable.white_pawn,R.drawable.white_pawn,
            R.drawable.white_pawn,R.drawable.white_pawn,
            R.drawable.white_rook,R.drawable.white_knight,
            R.drawable.white_bishop,R.drawable.white_queen,
            R.drawable.white_king,R.drawable.white_bishop,
            R.drawable.white_knight,R.drawable.white_rook
    };

    public Integer[] getmThumbIds() {
        return mThumbIds;
    }

    public void setmThumbIds(Integer[] mThumbIds) {
        this.mThumbIds = mThumbIds;
    }
}
