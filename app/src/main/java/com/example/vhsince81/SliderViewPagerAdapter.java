package com.example.vhsince81;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SliderViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private int mLayouts[];

    public SliderViewPagerAdapter(Context context, int mLayouts[])
    {
        this.context = context;
        this.layoutInflater = (LayoutInflater)this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
        this.mLayouts = mLayouts;
    }

    public int getCount(){

        return mLayouts.length;
    }

    public boolean isViewFromObject(View view, Object object){
        return view == ((View)object);
    }

    public Object instantiateItem(ViewGroup container, int  position){


        View view = layoutInflater.inflate(R.layout.layout_viewager_images,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(mLayouts[position]);
        container.addView(view);
        return view;

    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        View view  =(View) object;
        container.removeView(view);
    }

}
