package com.raza.householdrecharge.data.remote.mapper.account

import com.raza.householdrecharge.data.local.entity.AccountEntity
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.util.cleanString

object AccountEntityMapper {

    fun map(accountDto: AccountDto): AccountEntity {
        return AccountEntity(
            accountId = accountDto.accountId.cleanString(),
            accountName = accountDto.accountName.cleanString(),
            householdId = accountDto.householdId.cleanString()
        )
    }
}