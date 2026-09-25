package com.raza.householdrecharge.domain.error.domain

sealed interface MobileNumberDomainError {
    data object MobileNumberNotFound: MobileNumberDomainError //"mobile number is empty"
    data object MobileNumberIdNotFound: MobileNumberDomainError//"no mobile number id found"
}