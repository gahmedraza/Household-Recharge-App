package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.common.log
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository

class HouseholdUseCase(
    private val householdRepository: HouseholdRepository,
    private val accountRepository: AccountRepository
) {
    suspend fun addHouseholdAndUpdateAccount(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {

            //add household and receive householdId
            val addHouseholdResult = householdRepository.addHousehold(appUserDto, householdDto)
            val householdId: String

            when (addHouseholdResult) {
                is Result.Success -> {

                    householdId = addHouseholdResult.s.cleanString()
                }

                is Result.Failure -> {

                    onFailure("household id was not generated in household collection")
                    return
                }
            }

            //update user with householdId
            accountRepository.updateAccount(appUserDto.accountId, householdId)

            log("user collection updated with householdId")
            onSuccess(householdId)

        } catch (e: Exception) {

            onFailure(e.message.cleanString())
        }
    }

    suspend fun validateAccountAndJoinHousehold(
        accountDto: AccountDto?,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fetchAccountResult = accountRepository.fetchAccount(accountDto = accountDto)

        when(fetchAccountResult) {

            is com.raza.householdrecharge.core.result.Result.Success<*> -> {
                val accountDto = fetchAccountResult.s as AccountDto

                if(accountDto.householdId?.isEmpty() ?: false) {

                    onSuccess("success")
                } else {

                    onFailure("You are already member of another household")
                }
            }


            is com.raza.householdrecharge.core.result.Result.Failure<String> -> {
                onFailure("error in fetching account")
            }
        }
    }

    suspend fun `join_household_if_not_already`() {

    }
}