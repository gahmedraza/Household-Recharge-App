package com.raza.householdrecharge.core.logging

import android.util.Log
import com.raza.householdrecharge.util.cleanString

const val DEFAULT_TAG = "TAG"

fun log(message: String?) {
    Log.d(DEFAULT_TAG, message.cleanString())
}