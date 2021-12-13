package com.example.movieapp.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R

class SplashScreenActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startAnimation(findViewById(R.id.dot_left))
        startAnimation(findViewById(R.id.dot_center))
        startAnimation(findViewById(R.id.dot_right))

        val intent = Intent(this, MovieActivity::class.java)

        val connectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                startActivity(intent)
            }
            override fun onLost(network: Network) {
                Toast.makeText(baseContext, "No Network Connect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startAnimation(imageView: ImageView){
        val fadeInAnimation = AnimationUtils.loadAnimation(this , R.anim.fadein)
        fadeInAnimation.duration = 500
        imageView.startAnimation(fadeInAnimation)
    }
}