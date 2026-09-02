package com.raza.householdrecharge.common

import android.util.Log
import com.raza.householdrecharge.util.cleanString

const val DEFAULT_TAG = "TAG"

fun log(message: String?) {
    Log.d(DEFAULT_TAG, message.cleanString())
}