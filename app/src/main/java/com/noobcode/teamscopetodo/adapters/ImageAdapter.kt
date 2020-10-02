package com.noobcode.teamscopetodo.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.noobcode.teamscopetodo.utils.glide.GlideClass

class ImageAdapter(var mImages: Array<Int>, var mContext: Context?): PagerAdapter() {

    override fun getCount(): Int {
        return mImages.size;
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        GlideClass.loadImage(
            imageView,
            mImages[position],
            GlideClass.getProgressDrawable(mContext)
        )
        container.addView(imageView, 0)
        return imageView
    }


}