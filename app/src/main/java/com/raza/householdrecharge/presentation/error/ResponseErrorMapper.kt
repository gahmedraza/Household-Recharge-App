package com.raza.householdrecharge.presentation.error

import android.content.Context
import com.raza.householdrecharge.R
import com.raza.householdrecharge.domain.error.response.AuthResponseError.*
import com.raza.householdrecharge.domain.error.response.HouseholdResponseError.*
import com.raza.householdrecharge.domain.error.response.ResponseError
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResponseErrorMapper @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    fun map(error: ResponseError): String {

        if (error is UserNotCreated) {
            return context.getString(R.string.error_101)
        }

        if (error is UserNotLoggedIn) {
            return context.getString(R.string.error_102)
        }

        if (error is HouseholdIdNotGenerated) {
            return context.getString(R.string.error_103)
        }

        return context.getString(R.string.error_unhandled_exception)
    }
}