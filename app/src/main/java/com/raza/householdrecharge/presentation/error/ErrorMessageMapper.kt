package com.raza.householdrecharge.presentation.error

import com.raza.householdrecharge.R
import com.raza.householdrecharge.domain.error.domain.AppError
import com.raza.householdrecharge.domain.error.domain.HouseholdDomainError.HouseholdNotFound
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError.MobileNumberIdNotFound
import com.raza.householdrecharge.domain.error.domain.MobileNumberDomainError.MobileNumberNotFound
import com.raza.householdrecharge.domain.error.domain.UserDomainError.AccountIdNotFound
import com.raza.householdrecharge.domain.error.domain.UserDomainError.AuthIdNotFound
import javax.inject.Inject

class ErrorMessageMapper @Inject constructor(
) {

    fun map(error: AppError): UiMessage {

        if(error is AuthIdNotFound) {
            return UiMessage.ResourceId(R.string.error_auth_id_not_found)
        }

        if(error is AccountIdNotFound) {
            return UiMessage.ResourceId(R.string.error_account_id_not_found)
        }

        if(error is HouseholdNotFound) {
            return UiMessage.ResourceId(R.string.error_household_not_found)
        }

        if(error is MobileNumberNotFound) {
            return UiMessage.ResourceId(R.string.error_mobile_no_empty)
        }

        if(error is MobileNumberIdNotFound) {
            return UiMessage.ResourceId(R.string.error_mobile_id_empty)
        }

        return UiMessage.ResourceId(R.string.error_unhandled_exception)
    }
}