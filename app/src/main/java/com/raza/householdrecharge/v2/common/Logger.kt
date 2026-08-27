package com.raza.householdrecharge.v2.common

import android.util.Log
import com.raza.householdrecharge.v2.repository.cleanString

const val DEFAULT_TAG = "TAG"

fun log(message: String?) {
    Log.d(DEFAULT_TAG, message.cleanString())
}