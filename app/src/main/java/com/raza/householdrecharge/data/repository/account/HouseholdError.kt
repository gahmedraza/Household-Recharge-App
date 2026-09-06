package com.raza.householdrecharge.data.repository.account

sealed class HouseholdError {
    data object NoHouseholdFound: HouseholdError()
    data object DuplicateHousehold: HouseholdError()
    data object HouseholdDataMappingError: HouseholdError()
}