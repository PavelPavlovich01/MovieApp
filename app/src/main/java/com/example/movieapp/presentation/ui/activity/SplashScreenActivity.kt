package com.example.movieapp.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.movieapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimation(findViewById(R.id.dot_left), 500)
        startAnimation(findViewById(R.id.dot_center), 500)
        startAnimation(findViewById(R.id.dot_right), 500)

        //loadData()

        val intent = Intent(this, MovieActivity::class.java)
        startActivity(intent)
    }

    private fun startAnimation(imageView: ImageView, duration: Long){
        val fadeInAnimation = AnimationUtils.loadAnimation(this , R.anim.fadein)
        fadeInAnimation.duration = duration
        imageView.startAnimation(fadeInAnimation)
    }
}