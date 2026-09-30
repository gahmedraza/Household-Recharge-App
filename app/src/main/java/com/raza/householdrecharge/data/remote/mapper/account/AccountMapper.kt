package com.raza.householdrecharge.data.remote.mapper.account

import com.raza.householdrecharge.data.local.entity.AccountEntity
import com.raza.householdrecharge.data.remote.dto.AccountDto
import com.raza.householdrecharge.domain.model.Account

object AccountMapper {

    fun map(accountDto: AccountDto): Account {
        return Account(
            accountId = accountDto.accountId,
            accountName = accountDto.accountName,
            primaryMobileNumber = accountDto.primaryMobileNumber,
            householdId = accountDto.householdId
        )
    }

    fun map(accountEntity: AccountEntity): Account {
        return Account(
            accountId = accountEntity.accountId,
            accountName = accountEntity.accountName,
            primaryMobileNumber = accountEntity.primaryMobileNumber,
            householdId = accountEntity.householdId
        )
    }
}