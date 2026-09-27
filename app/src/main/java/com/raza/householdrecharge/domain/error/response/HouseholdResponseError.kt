package com.raza.householdrecharge.domain.error.response

sealed class HouseholdResponseError : ResponseError {
    data object NoHouseholdFound : HouseholdResponseError()

    data object HouseholdAlreadyAssigned : HouseholdResponseError()

    data object DuplicateHousehold : HouseholdResponseError()
    data object HouseholdDataMappingError : HouseholdResponseError()

    data object NoAccountFound : HouseholdResponseError()
    //fetching account during accounteligiblity check for household addition
    //this should be part of account error

    data class Unknown(val message: String) : HouseholdResponseError()

    data object HouseholdIdNotGenerated: HouseholdResponseError()
}