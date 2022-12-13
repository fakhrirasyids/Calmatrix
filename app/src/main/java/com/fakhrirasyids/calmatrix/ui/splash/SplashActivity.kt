package com.fakhrirasyids.calmatrix.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fakhrirasyids.calmatrix.databinding.ActivitySplashBinding
import com.fakhrirasyids.calmatrix.ui.main.MainActivity
import com.fakhrirasyids.calmatrix.utils.Constants.Companion.SPLASH_TIME_OUT

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}