package com.raza.householdrecharge.v1.util

import com.raza.householdrecharge.v1.data.MemberStatus
import com.raza.householdrecharge.v1.data.PlanStatus
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun calculateMemberStatus(
    expiryDate: Long?
): MemberStatus {

    if (expiryDate == null) {
        return MemberStatus(
            planStatus = PlanStatus.DUE,
            daysRemaining = 0
        )
    }

    val expiry = Instant
        .ofEpochMilli(expiryDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()

    val days = ChronoUnit.DAYS.between(
        today, expiry
    )

    val status = when {
        days > 0 -> PlanStatus.ACTIVE
        days == 0L -> PlanStatus.DUE
        else -> PlanStatus.EXPIRED
    }

    return MemberStatus(
        planStatus = status,
        daysRemaining = days
    )
}