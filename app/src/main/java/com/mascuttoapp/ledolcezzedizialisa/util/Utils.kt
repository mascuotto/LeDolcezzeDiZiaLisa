package com.mascuttoapp.ledolcezzedizialisa.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Utils {
    companion object {
        fun getMedia(mediaName: String): Uri {
            return Uri.parse(mediaName)
        }

        fun doInBackground( imageView: ImageView, progress: ProgressBar, iconUrl: String?) {
            val mainLooper = Looper.getMainLooper()
            //This coroutine download the image and work fine instead previous Async solution
            GlobalScope.launch {
                var image: Bitmap? = null
                try {
                    progress.visibility = View.VISIBLE
                    val `in` = java.net.URL(iconUrl).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                }
                catch (e: Exception) {
                    Log.e("Error Message", e.message.toString())
                    e.printStackTrace()
                }

                Handler(mainLooper).post {
                    imageView.setImageBitmap(image)
                    progress.visibility = View.GONE
                }
            }
        }
    }
}