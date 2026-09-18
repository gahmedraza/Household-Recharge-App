package com.raza.householdrecharge.data.remote.mapper.household

import com.raza.householdrecharge.data.local.entity.HouseholdEntity
import com.raza.householdrecharge.data.remote.dto.HouseholdDto

object HouseholdDtoMapper {

    fun map(householdEntity: HouseholdEntity): HouseholdDto {
        return HouseholdDto(
            householdName = householdEntity.householdName,
            authId = householdEntity.authId
        )
    }
}