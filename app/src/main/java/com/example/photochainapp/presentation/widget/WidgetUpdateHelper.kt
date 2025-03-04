package com.example.photochainapp.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent

object WidgetUpdateHelper {
    fun notifyWidgetUpdate(context: Context) {
        val intent = Intent(context, WidgetUpdateReceiver::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        }
        context.sendBroadcast(intent)
    }
}