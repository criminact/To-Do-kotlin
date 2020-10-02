package com.noobcode.teamscopetodo.utils.glide

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.noobcode.teamscopetodo.R

class GlideClass {

    companion object {
        fun loadImage(imageView: ImageView, url: Int, cpd: CircularProgressDrawable?) {
            val options: RequestOptions = RequestOptions()
            with(options) {
                placeholder(cpd)
                fitCenter()
                error(R.drawable.placeholder)
            }

            Glide.with(imageView.context)
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView)
        }

        fun getProgressDrawable(context: Context?): CircularProgressDrawable? {
            val cpd = CircularProgressDrawable(context!!)
            with(cpd) {
                strokeWidth = 5f
                centerRadius = 8f
                start()
            }
            return cpd
        }
    }

}