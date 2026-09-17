package com.raza.householdrecharge.core.logging

import android.util.Log
import com.raza.householdrecharge.util.cleanString

object Logger {
    const val DEFAULT_TAG = "TAG"

    fun log(message: String?) {
        log(DEFAULT_TAG, message.cleanString())
    }

    fun log(tag: String, message: String?) {
        Log.d(tag, message.cleanString())
    }
}