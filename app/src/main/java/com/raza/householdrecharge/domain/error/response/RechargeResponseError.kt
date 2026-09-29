package com.raza.householdrecharge.domain.error.response

import com.raza.householdrecharge.domain.error.usecase.RechargeUseCaseError

sealed interface RechargeResponseError : ResponseError,
    RechargeUseCaseError {
    data class Unknown(val reason: String) : RechargeResponseError
}