/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.brave.livedata.databinding.ActivityMainBinding
import com.brave.livedata.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            customizeSplashScreenExitAnimation()
        }
        keepSplashScreenForLongerPeriod()
    }

    private fun keepSplashScreenForLongerPeriod() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (mainViewModel.isDataReady()) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun customizeSplashScreenExitAnimation() {
        if (!BuildCompat.isAtLeastS()) {
            return
        }
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            /** Exit immediately **/
            //splashScreenView.remove()
            /** Exit using animation after particular duration **/
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.width.toFloat()
            )
            slideLeft?.interpolator = AnticipateInterpolator()
            slideLeft?.duration = 500L
            slideLeft?.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    splashScreenView.remove()
                }
            })
            slideLeft?.start()
        }
    }
}
