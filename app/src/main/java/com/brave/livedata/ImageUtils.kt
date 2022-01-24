/*
 *
 *  * Created by https://github.com/braver-tool
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 24/01/22, 11:45 AM
 *
 */

package com.brave.livedata


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.Log
import kotlin.math.min


class ImageUtils {
    companion object {

        /**
         * Method used to get maximum goal value
         * This method used for ClinicalStaffGraphBaseFragment.java and GraphBaseFragment.java
         *
         * @param context - Current Activity
         * @return leader profile as Bitmap
         */
        @SuppressLint("ResourceType")
        fun getLeaderProfile(context: Context, position: Int): Bitmap? {
            return try {
                @SuppressLint("Recycle") val typedArray: TypedArray =
                    context.resources.obtainTypedArray(R.array.leaderProfileArray)
                BitmapFactory.decodeResource(
                    context.resources,
                    typedArray.getResourceId(position, 15)
                )
            } catch (e: Exception) {
                Log.d("##getLeaderProfile", "------->Utils----->" + e.message)
                BitmapFactory.decodeResource(context.resources, R.drawable.default_profile)
            }
        }

        fun getCircularBitmap(srcBitmap: Bitmap): Bitmap? {
            // Calculate the circular bitmap width with border
            val squareBitmapWidth = min(srcBitmap.width, srcBitmap.height)
            // Initialize a new instance of Bitmap
            val dstBitmap = Bitmap.createBitmap(
                squareBitmapWidth,  // Width
                squareBitmapWidth,  // Height
                Bitmap.Config.ARGB_8888 // Config
            )
            val canvas = Canvas(dstBitmap)
            // Initialize a new Paint instance
            val paint = Paint()
            paint.isAntiAlias = true
            val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
            val rectF = RectF(rect)
            canvas.drawOval(rectF, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            // Calculate the left and top of copied bitmap
            val left = ((squareBitmapWidth - srcBitmap.width) / 2).toFloat()
            val top = ((squareBitmapWidth - srcBitmap.height) / 2).toFloat()
            canvas.drawBitmap(srcBitmap, left, top, paint)
            // Free the native object associated with this bitmap.
            srcBitmap.recycle()
            // Return the circular bitmap
            return dstBitmap
        }


        // Custom method to add a border around circular bitmap
        private fun addBorderToCircularBitmap(
            context: Context,
            srcBitmap: Bitmap,
            borderWidth: Int
        ): Bitmap? {
            // Calculate the circular bitmap width with border
            val dstBitmapWidth = srcBitmap.width + borderWidth * 2
            // Initialize a new Bitmap to make it bordered circular bitmap
            val dstBitmap =
                Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888)
            // Initialize a new Canvas instance
            val canvas = Canvas(dstBitmap)
            // Draw source bitmap to canvas
            canvas.drawBitmap(srcBitmap, borderWidth.toFloat(), borderWidth.toFloat(), null)
            // Initialize a new Paint instance to draw border
            val paint = Paint()
            paint.color = Color.parseColor("#FAFAFA")
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth.toFloat()
            paint.isAntiAlias = true
            canvas.drawCircle(
                (canvas.width / 2).toFloat(),  // cx
                (canvas.width / 2).toFloat(),  // cy
                (canvas.width / 2 - borderWidth / 2).toFloat(),  // Radius
                paint // Paint
            )
            // Free the native object associated with this bitmap.
            srcBitmap.recycle()
            // Return the bordered circular bitmap
            return dstBitmap
        }
    }
}