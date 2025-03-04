package com.example.photochainapp.presentation.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.widget.RemoteViews
import com.example.photochainapp.R
import com.example.photochainapp.data.repository.WidgetRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

/**
 * Implementation of App Widget functionality.
 */
class PhotoWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        context.let {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = WidgetRepositoryImpl(it)
                val imageUri = repository.getImageUri().firstOrNull()

                withContext(Dispatchers.Main) {
                    appWidgetIds.forEach { appWidgetId ->
                        updateAppWidget(it, appWidgetManager, appWidgetId, imageUri)
                    }
                }
            }
        }

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            imageUri: String?
        ) {
            val remoteViews = RemoteViews(context.packageName, R.layout.photo_widget)

            imageUri?.let {
                val bitmap = loadBitmapWithFixOrientation(context, Uri.parse(it))
                if (bitmap != null) remoteViews.setImageViewBitmap(R.id.appwidget_image, bitmap)
            }


            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }


        private fun loadBitmapWithFixOrientation(context: Context, uri: Uri): Bitmap? {
            val bitmap = loadBitmapFromUri(context, uri)
            return if (bitmap != null) {
                fixImageOrientation(context, uri, bitmap)
            } else {
                null
            }
        }

        private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
            return try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val options = BitmapFactory.Options().apply {
                        inJustDecodeBounds = true
                    }
                    BitmapFactory.decodeStream(inputStream, null, options) // Get image dimensions

                    // Calculate sample size to scale down the image
                    val reqWidth = 300 // Adjust width based on your widget size
                    val reqHeight = 300 // Adjust height based on your widget size
                    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

                    options.inJustDecodeBounds = false
                    context.contentResolver.openInputStream(uri)?.use { resizedStream ->
                        BitmapFactory.decodeStream(
                            resizedStream,
                            null,
                            options
                        ) // Decode smaller image
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2

                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }


        private fun fixImageOrientation(context: Context, imageUri: Uri, originalBitmap: Bitmap): Bitmap? {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val exif = ExifInterface(inputStream!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            // Rotate image based on EXIF orientation
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
                ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
            }

            // Create a new Bitmap with the corrected orientation
            return Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)
        }
    }
}