package com.example.bccintern2.firsttime

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.bccintern2.picasso.CircleTransform
import com.example.bccintern3.R
import com.squareup.picasso.Picasso

class PointThree: Fragment(R.layout.pointthree_fragment) {
    private lateinit var imageView: ImageView

    fun init(view: View){
        imageView = view.findViewById(R.id.onboardingthree_image)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setImage(view)
    }

    fun setImage(view: View){
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val size = (view.width/1.5).toInt()
                Picasso
                    .get()
                    .load(R.drawable.splash3)
                    .resize(size,size)
                    .transform(CircleTransform())
                    .centerCrop()
                    .into(imageView)
            }
        })
    }
}