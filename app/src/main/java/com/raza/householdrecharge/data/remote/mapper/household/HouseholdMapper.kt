package com.raza.householdrecharge.data.remote.mapper.household

import com.raza.householdrecharge.data.local.entity.HouseholdEntity
import com.raza.householdrecharge.domain.model.Household

object HouseholdMapper {

    fun map(householdEntity: HouseholdEntity): Household {
        return Household(
            householdId = householdEntity.householdId,
            householdName = householdEntity.householdName,
            accountId = householdEntity.accountId
        )
    }
}