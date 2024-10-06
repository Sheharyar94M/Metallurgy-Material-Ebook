package com.qsa.metallurgy_material_engineering_premium.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qsa.metallurgy_material_engineering_premium.R;

public class MainAdapter extends BaseAdapter {

    private Context context;
    public int[] photo_get;
    LayoutInflater inflter;

    @Override
    public int getCount() {
        return photo_get.length;
    }

    public MainAdapter(Context context, int[] p) {
        this.photo_get = p;
        this.context = context;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public Object getItem(int i) {
        return photo_get[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        ImageView imageView = new ImageView(context);
//        imageView.setImageResource(photo_get[i]);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setLayoutParams(new GridView.LayoutParams(280,350));
//        return imageView;
        view = inflter.inflate(R.layout.custom_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        icon.setImageResource(photo_get[i]); // set logo images
        return view;
    }
}
