package com.raza.householdrecharge.v1.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter
    .ofPattern("MMM dd, yyyy")
    .withZone(ZoneId.systemDefault())

fun formatDate(timeStamp: Long?): String {
    return timeStamp?.let {
        formatter.format(Instant.ofEpochMilli(it))
    } ?: ""
}