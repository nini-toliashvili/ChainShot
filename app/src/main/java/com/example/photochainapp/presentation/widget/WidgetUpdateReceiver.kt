package com.example.photochainapp.presentation.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.example.photochainapp.data.repository.WidgetRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WidgetUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val appWidgetManager = AppWidgetManager.getInstance(it)
            val componentName = ComponentName(it, PhotoWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

            CoroutineScope(Dispatchers.IO).launch {
                val repository = WidgetRepositoryImpl(it)
                val imageUri = repository.getImageUri().firstOrNull()

                withContext(Dispatchers.Main) {
                    appWidgetIds.forEach { appWidgetId ->
                        PhotoWidgetProvider.updateAppWidget(it, appWidgetManager, appWidgetId, imageUri)
                    }
                }
            }
        }
    }

}
