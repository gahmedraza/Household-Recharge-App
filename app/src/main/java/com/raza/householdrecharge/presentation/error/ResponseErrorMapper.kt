package com.raza.householdrecharge.presentation.error

import com.raza.householdrecharge.R
import com.raza.householdrecharge.domain.error.response.AuthResponseError.*
import com.raza.householdrecharge.domain.error.response.HouseholdResponseError.*
import com.raza.householdrecharge.domain.error.response.ResponseError
import javax.inject.Inject

class ResponseErrorMapper @Inject constructor(
) {

    fun map(error: ResponseError): UiMessage {

        if (error is UserNotCreated) {
            return UiMessage.ResourceId(R.string.error_101)
        }

        if (error is UserNotLoggedIn) {
            return UiMessage.ResourceId(R.string.error_102)
        }

        if (error is HouseholdIdNotGenerated) {
            return UiMessage.ResourceId(R.string.error_103)
        }

        return UiMessage.ResourceId(R.string.error_unhandled_exception)
    }
}