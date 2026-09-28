package com.raza.householdrecharge.domain.error.request

sealed interface MobileNumberRequestError : RequestError {
    data object MobileNumberNotFound: MobileNumberRequestError
    data object MobileNumberIdNotFound: MobileNumberRequestError
}