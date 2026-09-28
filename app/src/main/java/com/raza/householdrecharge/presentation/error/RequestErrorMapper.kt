package com.raza.householdrecharge.presentation.error

import android.content.Context
import com.raza.householdrecharge.R
import com.raza.householdrecharge.domain.error.request.RequestError
import com.raza.householdrecharge.domain.error.request.HouseholdRequestError.HouseholdNotFound
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError.MobileNumberIdNotFound
import com.raza.householdrecharge.domain.error.request.MobileNumberRequestError.MobileNumberNotFound
import com.raza.householdrecharge.domain.error.request.UserRequestError.AccountIdNotFound
import com.raza.householdrecharge.domain.error.request.UserRequestError.AuthIdNotFound
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RequestErrorMapper @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    fun map(error: RequestError): String {

        if(error is AuthIdNotFound) {
            return context.getString(R.string.error_auth_id_not_found)
        }

        if(error is AccountIdNotFound) {
            return context.getString(R.string.error_account_id_not_found)
        }

        if(error is HouseholdNotFound) {
            return context.getString(R.string.error_household_not_found)
        }

        if(error is MobileNumberNotFound) {
            return context.getString(R.string.error_mobile_no_empty)
        }

        if(error is MobileNumberIdNotFound) {
            return context.getString(R.string.error_mobile_id_empty)
        }

        return context.getString(R.string.error_unhandled_exception)
    }
}