package com.raza.householdrecharge.common

import com.raza.householdrecharge.domain.model.Member

fun map(id: String, memberDto: MemberDto?): Member {
    val member = Member(
        id = id,

        name = memberDto?.name ?: "",

        mobileNumber = memberDto?.mobileNumber ?: "",

        planDurationDays =
            if (memberDto?.planDurationDays?.isEmpty() ?: false) {
                0
            } else {
                memberDto?.planDurationDays?.toInt() ?: 0
            },

        lastRechargeDate =
            if (memberDto?.lastRechargeDate?.isEmpty() ?: false) {
                0
            } else {
                getDateInMillis(
                    memberDto?.lastRechargeDate
                )
            },

        planExpiryDate =
            if (memberDto?.planExpiryDate?.isEmpty() ?: false) {
                0
            } else {
                getDateInMillis(
                    memberDto?.planExpiryDate
                )
            },

        rechargeRequested = false,

        planAmount =
            if (memberDto?.planAmount?.isEmpty() ?: false) {
                0
            } else {
                memberDto?.planAmount?.toInt() ?: 0
            }
    )

    return member
}