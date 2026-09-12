package com.raza.householdrecharge.domain.error

sealed class MobileNumberRemoteDataSourceError {

    data object MobileNumberDoesNotExist : MobileNumberRemoteDataSourceError()

    data object MobileNumberParsingError : MobileNumberRemoteDataSourceError()

    data object UnknownError : MobileNumberRemoteDataSourceError()
}