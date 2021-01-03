package com.mascuttoapp.ledolcezzedizialisa.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

@SuppressLint("StaticFieldLeak")
@Suppress("DEPRECATION")
 class DownloadImageFromInternet(var imageView: ImageView, var progress: ProgressBar) : AsyncTask<String, Void, Bitmap?>() {

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
            progress.visibility = View.GONE
        }

        override fun doInBackground(vararg urls: String?): Bitmap? {
            var image: Bitmap? = null
            try {
                progress.visibility = View.VISIBLE
                val imageURL = urls[0]
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
}

class Utils {
    companion object {
        fun getMedia(mediaName: String): Uri {
            return Uri.parse(mediaName)
        }
    }
}