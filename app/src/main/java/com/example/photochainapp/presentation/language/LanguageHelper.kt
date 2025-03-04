package com.example.photochainapp.presentation.language

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageHelper {
    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)

        val context = activity.createConfigurationContext(config)
        activity.resources.updateConfiguration(config, context.resources.displayMetrics)


        val sharedPreferences = activity.getSharedPreferences("settings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", languageCode).apply()


        activity.recreate()
    }

     fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }

    fun wrapContext(context: Context): Context {
        val languageCode = getSavedLanguage(context)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}