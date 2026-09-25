package com.raza.householdrecharge.domain.validator.request

import com.raza.householdrecharge.domain.error.request.AccountRequestError.*
import javax.inject.Inject
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.domain.error.domain.UserDomainError.*
import com.raza.householdrecharge.domain.error.request.AccountRequestError

class AccountRequestValidator @Inject constructor(
) {

    fun validate(
        accountId: String
    ): Result<Unit, AccountRequestError> {

        if (accountId.isEmpty()) {
            return Result.Failure(UserError(AccountIdNotFound))
        }

        return Result.Success(Unit)
    }
}