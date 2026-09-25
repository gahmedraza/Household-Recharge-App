package com.raza.householdrecharge.domain.error.request

import com.raza.householdrecharge.domain.error.domain.UserDomainError

sealed interface AccountRequestError {
    data class UserError(val error: UserDomainError) : AccountRequestError
}