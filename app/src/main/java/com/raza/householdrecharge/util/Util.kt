package com.raza.householdrecharge.util

fun String?.cleanString(): String {
    return this ?: ""
}

fun String?.noContent(): Boolean {
    return this?.isEmpty() ?: false
}