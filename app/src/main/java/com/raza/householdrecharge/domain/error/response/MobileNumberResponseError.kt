package com.raza.householdrecharge.domain.error.response

import com.raza.householdrecharge.domain.error.usecase.MobileNumberUseCaseError
import com.raza.householdrecharge.domain.error.usecase.RechargeUseCaseError

sealed interface MobileNumberResponseError : ResponseError,
    MobileNumberUseCaseError, RechargeUseCaseError {
    data class Unknown(val reason: String): MobileNumberResponseError
}