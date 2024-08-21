package com.kisia.pets_word

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo)

        val logoImageView: ImageView = findViewById(R.id.logo)
        val fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        // 애니메이션 시작
        logoImageView.startAnimation(fadeOutAnimation)

        // 애니메이션이 끝난 후 메인 액티비티로 전환
        fadeOutAnimation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                startActivity(Intent(this@SplashActivity, NotificationTest::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
    }
}
