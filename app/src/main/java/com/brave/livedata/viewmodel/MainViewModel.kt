/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata.viewmodel

import android.os.SystemClock
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    companion object {
        const val WORK_DURATION = 1500L
    }

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION
}