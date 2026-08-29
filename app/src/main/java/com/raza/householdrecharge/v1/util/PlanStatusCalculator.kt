package com.raza.householdrecharge.v1.util

import com.raza.householdrecharge.v1.data.PlanStatus
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun calculatePlanStatus(
    expiryDate: Long?
): PlanStatus {
    if (expiryDate == null) {
        return PlanStatus.DUE
    }

    val expiry = Instant
        .ofEpochMilli(expiryDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()

    return when {
        today.isBefore(expiry) -> PlanStatus.ACTIVE
        today.isEqual(expiry) -> PlanStatus.DUE
        else -> PlanStatus.EXPIRED
    }
}