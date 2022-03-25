/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 25/03/22, 11:00 AM
 *
 */

package com.brave.livedata

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View

class GitHubLogoView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {
    private var mBackgroundCanvas: Canvas?
    private var mBackground: Bitmap? = null
    private var mMask: Bitmap? = BitmapFactory.decodeResource(resources, R.drawable.githublogo)
    private var mPaint: Paint? = Paint()
    private var mDrawingRect: Rect? = null
    private var mDirection = false
    private var mLastChange: Long = 0
    private var mDuration = 2000
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mDrawingRect = Rect(0, 0, w, h)
        val mask = convertToAlphaMask(mMask?.let { Bitmap.createScaledBitmap(it, w, h, false) })
        mPaint?.shader = mask?.let { BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP) }
        mBackground = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mBackgroundCanvas?.setBitmap(mBackground)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val time = SystemClock.uptimeMillis()
        val diff = (time - mLastChange).toInt()
        if (time - mLastChange > mDuration) {
            mDirection = !mDirection
            mLastChange = time
        }
        val percent = (if (mDirection) diff % mDuration else mDuration - diff % mDuration) / mDuration.toFloat()
        //mPaint?.color = ARGB_EVAL.evaluate(percent, Color.LTGRAY, Color.YELLOW) as Int
        mPaint?.color = Color.parseColor("#610692")
        // Draw the masked logo in the new color
        //mBackgroundCanvas?.drawRect(mDrawingRect, mPaint)
        mDrawingRect?.let { mPaint?.let { it1 -> mBackgroundCanvas?.drawRect(it, it1) } }
        // Draw the new logo
        mBackground?.let { canvas?.drawBitmap(it, 0f, 0f, mPaint) }
        invalidate()
    }

    companion object {
        private val ARGB_EVAL: ArgbEvaluator = ArgbEvaluator()
        private fun convertToAlphaMask(b: Bitmap?): Bitmap? {
            val a = b?.let { Bitmap.createBitmap(it.width, b.height, Bitmap.Config.ALPHA_8) }
            val c = a?.let { Canvas(it) }
            c?.drawBitmap(b, 0.0f, 0.0f, null)
            return a
        }
    }

    init {
        mPaint?.color = -0x10000
        mPaint?.isAntiAlias = true
        mBackgroundCanvas = Canvas()
    }
}