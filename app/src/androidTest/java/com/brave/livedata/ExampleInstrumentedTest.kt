/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.brave.livedata", appContext.packageName)
    }
}