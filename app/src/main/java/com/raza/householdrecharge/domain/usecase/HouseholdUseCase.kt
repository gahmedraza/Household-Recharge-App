package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.account.AccountError

class HouseholdUseCase(
    private val householdRepository: HouseholdRepository,
    private val accountRepository: AccountRepository
) {
    suspend fun addHouseholdAndUpdateAccount(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, String> {
        var result: Result<String, String>

        try {

            //add household and receive householdId
            val addHouseholdResult = householdRepository.addHousehold(appUserDto, householdDto)
            var householdId = ""

            when (addHouseholdResult) {
                is Result.Success -> {

                    householdId = addHouseholdResult.data.cleanString()
                }

                is Result.Failure -> {

                    result = Result.Failure("household id was not generated in household collection")
                }
            }

            //update user with householdId
            accountRepository.updateAccount(appUserDto.accountId, householdId)

            log("user collection updated with householdId")
            result = Result.Success(householdId)

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun validateAccountAndJoinHousehold(
        accountId: String
    ): Result<String, String> {

        var result: Result<String, String>

        try {

            val fetchAccountResult = accountRepository
                .fetchAccount(
                    collectionId = accountId
                )

            when(fetchAccountResult) {

                is Result.Success<AccountDto> -> {
                    val accountDto = fetchAccountResult.data

                    if(accountDto.householdId?.isEmpty() ?: false) {

                        result = Result.Success("success")
                    } else {

                        result = Result.Failure("You are already member of another household")
                    }
                }


                is Result.Failure<AccountError> -> {
                    result = Result.Failure("error in fetching account")
                }
            }

        } catch (e: Exception) {

            result = Result.Failure(e.message.cleanString())
        }

        return result
    }

    suspend fun `join_household_if_not_already`() {

    }
}