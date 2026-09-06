package com.raza.householdrecharge.data.repository.account

sealed class HouseholdError {
    data object NoHouseholdFound: HouseholdError()

    data object HouseholdAlreadyAssigned: HouseholdError()

    data object DuplicateHousehold: HouseholdError()
    data object HouseholdDataMappingError: HouseholdError()

    data object NoAccountFound: HouseholdError()
    //fetching account during accounteligiblity check for household addition
    //this should be part of account error

    data object Unknown: HouseholdError()
}