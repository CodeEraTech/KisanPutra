package com.shambhu.kisanputra.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shambhu.kisanputra.R;

import java.util.List;

public class ImageViewPagerAdapter extends PagerAdapter {

    Context context;
    List<String> urlList;

    public ImageViewPagerAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container,final int i) {
        ImageView mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        requestOptions.apply(RequestOptions.noTransformation());


       Glide.with(context.getApplicationContext()).applyDefaultRequestOptions(requestOptions)
               .load(urlList.get(i))
               .apply(RequestOptions.noTransformation()).into(mImageView);


        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }


}
