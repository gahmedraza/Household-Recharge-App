package com.raza.householdrecharge.util

import java.time.LocalDate
import java.time.ZoneId

fun String?.cleanString(): String {
    return this ?: ""
}

fun String?.noContent(): Boolean {
    return this?.isEmpty() ?: false
}

fun isLessThanSpecifiedMonths(
    date: Long,
    months: Long = 1
): Boolean {
    val rechargeDateLowerBound = LocalDate
        .now()
        .minusMonths(months)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    if (date < rechargeDateLowerBound) {
        return true
    }

    return false
}

fun isMoreThanSpecifiedMonths(
    date: Long,
    months: Long = 13
): Boolean {
    val rechargeDateUpperBound = LocalDate
        .now()
        .plusMonths(months)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    if (date > rechargeDateUpperBound) {
        return true
    }

    return false
}