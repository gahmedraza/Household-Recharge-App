package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AccountEligibilityDto
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.domain.error.response.HouseholdResponseError
import com.raza.householdrecharge.domain.error.usecase.HouseholdUseCaseError
import com.raza.householdrecharge.domain.model.FindHouseholdResponse
import com.raza.householdrecharge.domain.model.Household
import com.raza.householdrecharge.domain.validator.response.InvitationResponseValidator
import com.raza.householdrecharge.util.cleanString
import javax.inject.Inject

class HouseholdUseCase @Inject constructor(
    private val householdRepository: HouseholdRepository,
    private val accountRepository: AccountRepository,
    private val invitationRepository: InvitationRepository,
    private val invitationResponseValidator: InvitationResponseValidator
) {
    suspend fun createHouseholdAndUpdateAccount(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String, HouseholdUseCaseError> {

        try {

            //add household and receive householdId
            val addHouseholdResult = householdRepository.addHousehold(appUserDto, householdDto)
            var householdId = ""

            when (addHouseholdResult) {
                is Result.Success -> {

                    householdId = addHouseholdResult.data.cleanString()
                }

                is Result.Failure -> {

                    return Result.Failure(
                        HouseholdResponseError.HouseholdIdNotGenerated
                    )
                }
            }

            //update user with householdId
            accountRepository.updateAccount(appUserDto.accountId, householdId)

            //Logger.log("user collection updated with householdId") //todo
            return Result.Success(householdId)

        } catch (e: Exception) {

            return Result.Failure(
                HouseholdResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }

    suspend fun isAccountEligibleToJoinHousehold(
        accountId: String
    ): Result<AccountEligibilityDto, HouseholdUseCaseError> {

        try {

            val accountResult = accountRepository.fetchAccountByAccountId(
                    accountId = accountId
                )

            if(accountResult is Result.Failure) {
                Logger.log("error in fetching account")
                return Result.Failure(HouseholdResponseError.NoAccountFound)
            }

            val account = (accountResult as Result.Success).data

            if (account.householdId.isNullOrEmpty()) {

                return Result.Success(AccountEligibilityDto(
                    isEligible = true,
                    account = account
                ))

            } else {

                //Logger.log("Account already member of another household") //todo
                //return Result.Failure(HouseholdResponseError.HouseholdAlreadyAssigned)
                return Result.Success(AccountEligibilityDto(
                    isEligible = false,
                    account = account
                ))
            }

        } catch (e: Exception) {

            val error = e.message.cleanString()
            //Logger.log(error) //todo
            return Result.Failure(HouseholdResponseError.Unknown(error))
        }
    }

    suspend fun validateInvitationAndFindLinkedHousehold(
        authId: String,
        invitationCode: String
    ): Result<FindHouseholdResponse?, HouseholdUseCaseError> {

        try {

            val result2 = invitationRepository.getInvitationByInvitationCode(
                invitationCode
            )

            if(result2 is Result.Failure) {
                return result2
            }

            val invitation = (result2 as Result.Success).data

            val result3 = invitationResponseValidator.validate(
                invitationDto = invitation
            )

            if(result3 is Result.Failure) {
                return result3
            }

            val householdId = invitation.householdId

            val result4 = householdRepository.getHouseholdByHouseholdId(householdId)

            if(result4 is Result.Failure) {
                return result4
            }

            val household = (result4 as Result.Success).data

            val findHouseholdResponse = FindHouseholdResponse(
                householdId = invitation.householdId,
                householdName = household.householdName.cleanString(),
                invitationCode = invitation.code
            )

            return Result.Success(findHouseholdResponse)

        } catch (e: Exception) {

            return Result.Failure(
                HouseholdResponseError.Unknown(
                    e.message.cleanString()
                )
            )
        }
    }

    //todo get invitation is done 2'ce
    suspend fun validateInvitationAndUpdateAccountAndMarkUsed(
        userId: String,
        householdId: String,
        invitationCode: String
    ): Result<String, HouseholdUseCaseError> {

        //validate invitation for all fields
        val result51 = invitationRepository.getInvitationByInvitationCode(invitationCode)

        if(result51 is Result.Failure) {
            //return Result.Failure(result51.error.toString())
            return result51
        }

        val invitationDto = (result51 as Result.Success).data

        val result52 = invitationResponseValidator.validate(invitationDto, householdId)

        if(result52 is Result.Failure) {
            return result52
        }

        //todo should this be last statement?
        //update account with household id
        accountRepository.updateAccount(userId, householdId)

        //mark invitation used in invitation document

        //todo should this be elsewhere?
        val updatedInvitationDto = invitationDto.copy(
            status = "used",
            usedBy = userId,
            usedAt = System.currentTimeMillis()
        )

        invitationRepository.updateInvitation(updatedInvitationDto)

        return Result.Success("success")
    }

    suspend fun getHouseholdByHouseholdId(
        householdId: String
    ): Result<Household, HouseholdResponseError>{

        return householdRepository.getHouseholdByHouseholdId(
            householdId
        )
    }
}