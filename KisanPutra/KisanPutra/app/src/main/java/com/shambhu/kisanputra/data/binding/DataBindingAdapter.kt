package com.ondemand.data.binding;

import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.ui.adapters.ImageViewPagerAdapter
import com.shambhu.kisanputra.ui.adapters.SliderViewPagerAdapter

class DataBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("bind:loadimage")
        fun loadimage(view: ImageView, url: String) {
            Glide.with(view.context.applicationContext).load("" + url)
                .apply(RequestOptions.noTransformation()).into(view)
        }

        @BindingAdapter("bind:setCircularImage")
        fun setCircularImage(thumbnails: ImageView, url: String) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.logo_2)
            requestOptions.error(R.drawable.logo_2)
            // requestOptions.transforms(RequestOptions.circleCropTransform());

            Glide.with(thumbnails.context.applicationContext)
                .setDefaultRequestOptions(requestOptions).load(url)
                .apply(RequestOptions.circleCropTransform()).into(thumbnails)
        }

        @JvmStatic
        @BindingAdapter("bind:load_ResourceImage")
        public fun load_ResourceImage(@NonNull imageView : ImageView, @NonNull res : Int?){
            if (res!=-1)
                Glide.with(imageView.context).load(res).into(imageView)
        }

        @BindingAdapter("bind:setLinnearHight")
        fun setLinnearHight(linearLayout: LinearLayout, isset: Boolean) {

            if (isset) {
                val displayMetrics: DisplayMetrics
                val screenHeight: Double
                val screenWidth: Double
                val lhi: Int
                val lwi: Int

                displayMetrics = linearLayout.context.resources.displayMetrics
                screenHeight = displayMetrics.heightPixels.toDouble()
                //  screenWidth = displayMetrics.widthPixels;

                lhi = (screenHeight * 0.08).toInt()
                // lwi = (int) (screenWidth * 0.02);

                linearLayout.layoutParams.height = lhi
                //   toolbar.getLayoutParams().width = lwi;
                linearLayout.requestLayout()

            }
        }

        @BindingAdapter("bind:setRelativeHight")
        fun setRelativeHight(relativeLayout: RelativeLayout, isset: Boolean) {

            if (isset) {
                val displayMetrics: DisplayMetrics
                val screenHeight: Double
                val screenWidth: Double
                val lhi: Int
                val lwi: Int

                displayMetrics = relativeLayout.context.resources.displayMetrics
                screenHeight = displayMetrics.heightPixels.toDouble()
                //  screenWidth = displayMetrics.widthPixels;

                lhi = (screenHeight * 0.12).toInt()
                // lwi = (int) (screenWidth * 0.02);

                relativeLayout.layoutParams.height = lhi
                //   toolbar.getLayoutParams().width = lwi;
                relativeLayout.requestLayout()

            }
        }


        @JvmStatic
        @BindingAdapter("bind:setSlider")
        fun setSlider(view: ViewPager, imageArray: List<HomeProductResponse.Slider>) {
            if (imageArray != null) {
                if (!imageArray.isEmpty()) {

                   // val imageList : ArrayList<String> = arrayListOf()
                  //  imageArray.forEach{imageList.add(it.image)}

                    if (imageArray.size > 1) {
                        view.clipToPadding = false
                        view.pageMargin = 40
                        view.setPadding(0, 0, 100, 0)
                        view.offscreenPageLimit = 2
                    }

                    view.adapter = SliderViewPagerAdapter(view.context, imageArray)
                }
            }
        }

    }

}
