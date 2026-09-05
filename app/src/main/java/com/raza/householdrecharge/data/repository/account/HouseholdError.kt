package com.raza.householdrecharge.data.repository.account

sealed class HouseholdError {
    data object DuplicateHousehold: HouseholdError()
}