package com.raza.householdrecharge.domain.error.response

sealed class HouseholdResponseError {
    data object NoHouseholdFound: HouseholdResponseError()

    data object HouseholdAlreadyAssigned: HouseholdResponseError()

    data object DuplicateHousehold: HouseholdResponseError()
    data object HouseholdDataMappingError: HouseholdResponseError()

    data object NoAccountFound: HouseholdResponseError()
    //fetching account during accounteligiblity check for household addition
    //this should be part of account error

    data object Unknown: HouseholdResponseError()
}