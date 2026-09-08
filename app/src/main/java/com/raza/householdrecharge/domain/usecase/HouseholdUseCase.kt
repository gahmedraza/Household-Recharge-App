package com.raza.householdrecharge.domain.usecase

import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.util.cleanString
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AccountEligibilityDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.data.repository.account.HouseholdError
import com.raza.householdrecharge.data.repository.account.InvitationError
import com.raza.householdrecharge.data.repository.account.PENDING
import com.raza.householdrecharge.domain.error.HouseholdUseCaseError

class HouseholdUseCase(
    private val householdRepository: HouseholdRepository,
    private val accountRepository: AccountRepository,
    private val invitationRepository: InvitationRepository
) {
    suspend fun createHouseholdAndUpdateAccount(
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

    suspend fun isAccountEligibleToJoinHousehold(
        accountId: String
    ): Result<AccountEligibilityDto, HouseholdError> {

        var result: Result<AccountEligibilityDto, HouseholdError>

        try {

            val accountResult = accountRepository.fetchAccountByAccountId(
                    accountId = accountId
                )

            if(accountResult is Result.Failure) {
                log("error in fetching account")
                return Result.Failure(HouseholdError.NoAccountFound)
            }

            val accountDto = (accountResult as Result.Success).data

            if (accountDto.householdId.isNullOrEmpty()) {

                result = Result.Success(AccountEligibilityDto(
                    isEligible = true,
                    accountDto = accountDto
                ))

            } else {

                log("Account already member of another household")
                result = Result.Failure(HouseholdError.HouseholdAlreadyAssigned)
            }

        } catch (e: Exception) {

            log(e.message)
            result = Result.Failure(HouseholdError.Unknown)
        }

        return result
    }

    suspend fun `join_household_if_not_already`() {

    }

    //
    suspend fun validateInvitationAndFindLinkedHousehold(
        authId: String,
        invitationCode: String
    ): Result<HouseholdDto, HouseholdUseCaseError> {

        var result: Result<HouseholdDto, HouseholdUseCaseError>

        try {

            val result2 = invitationRepository.getInvitationByInvitationCode(
                invitationCode
            )

            if(result2 is Result.Failure) {
                return Result.Failure(
                    HouseholdUseCaseError.Invitation(
                        result2.error
                    )
                )
            }

            val invitation = (result2 as Result.Success).data

            val result3 = validateInvitation(
                invitation = invitation
            )

            if(result3 is Result.Failure) {
                return Result.Failure(
                    HouseholdUseCaseError.Invitation(
                        result3.error
                    )
                )
            }

            val householdId = invitation.householdId

            val result4 = householdRepository.getHouseholdByHouseholdId(householdId)

            if(result4 is Result.Failure) {
                return Result.Failure(
                    HouseholdUseCaseError.Household(
                        result4.error
                    )
                )
            }

            val household = (result4 as Result.Success).data

            household.invitationCode = invitationCode

            result = Result.Success(household)

        } catch (e: Exception) {

            result = Result.Failure(
                HouseholdUseCaseError.Unknown(
                    e.message.cleanString()
                )
            )
        }

        return result
    }

    private fun validateInvitation(
        invitation: InvitationDto?,
    ): Result<Unit, InvitationError> {

        if(invitation == null) {
            return Result.Failure(InvitationError.InvitationCodeNotFound)
        }

        if(invitation.status != PENDING) {
            return Result.Failure(InvitationError.InvitationAlreadyUsed)
        }

        val invitationExpiry = invitation.expiresAt.toLong()

        if(invitationExpiry < System.currentTimeMillis()) {
            return Result.Failure(InvitationError.InvitationExpired)
        }

        return Result.Success(Unit)
    }
    //
}