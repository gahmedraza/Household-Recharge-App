package com.raza.householdrecharge.data.remote.mapper.account

import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.model.Account

object AccountMapper {

    fun map(accountDto: AccountDto): Account {
        return Account(
            accountId = accountDto.accountId,
            accountName = accountDto.accountName,
            householdId = accountDto.householdId
        )
    }
}