package com.doan.banhang.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PictureIntroduceAdapter extends PagerAdapter {

    private ArrayList<String> arrayPicture;

    public PictureIntroduceAdapter(ArrayList<String> arrayPicture) {
        this.arrayPicture = arrayPicture;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return arrayPicture.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imgPicture = new ImageView(container.getContext());
        imgPicture.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Glide.with(container.getContext())
                .load(arrayPicture.get(position))
                .into(imgPicture);

        container.addView(imgPicture);

        return imgPicture;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}