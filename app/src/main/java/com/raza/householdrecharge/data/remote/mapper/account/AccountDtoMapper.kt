package com.raza.householdrecharge.data.remote.mapper.account

import com.raza.householdrecharge.data.local.entity.AccountEntity
import com.raza.householdrecharge.data.remote.dto.AccountDto

object AccountDtoMapper {

    fun map(accountEntity: AccountEntity): AccountDto {
        return AccountDto(
            accountId = accountEntity.accountId,
            accountName = accountEntity.accountName,
            householdId = accountEntity.householdId
        )
    }
}