package com.raza.householdrecharge.v2.common

import android.util.Log
import com.raza.householdrecharge.v2.util.cleanString

const val DEFAULT_TAG = "TAG"

fun log(message: String?) {
    Log.d(DEFAULT_TAG, message.cleanString())
}