package com.example.pascalso.tester;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Pascal So on 7/23/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(600, 600));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
            imageView.setMaxHeight(100);
            imageView.setMaxWidth(100);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_action_biology, R.drawable.ic_action_chemistry,
            R.drawable.ic_action_physics, R.drawable.ic_action_math,
            R.drawable.ic_action_biology, R.drawable.ic_action_chemistry,
            R.drawable.ic_action_physics, R.drawable.ic_action_math,
            R.drawable.ic_action_biology, R.drawable.ic_action_chemistry,
            R.drawable.ic_action_physics, R.drawable.ic_action_math,
            R.drawable.ic_action_biology, R.drawable.ic_action_chemistry,
            R.drawable.ic_action_physics, R.drawable.ic_action_math,
            R.drawable.ic_action_biology, R.drawable.ic_action_chemistry,
            R.drawable.ic_action_physics, R.drawable.ic_action_math,

    };
}