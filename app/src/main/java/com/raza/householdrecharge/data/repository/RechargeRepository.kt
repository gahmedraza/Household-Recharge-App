package com.raza.householdrecharge.data.repository

import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.local.dao.RechargeDao
import com.raza.householdrecharge.data.remote.datasource.RechargeRemoteDataSource
import com.raza.householdrecharge.data.remote.dto.RechargeDto
import com.raza.householdrecharge.data.remote.mapper.recharge.RechargeEntityMapper
import com.raza.householdrecharge.data.remote.mapper.recharge.RechargeMapper
import com.raza.householdrecharge.domain.model.Recharge
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RechargeRepository @Inject constructor(
    private val rechargeRemoteDataSource: RechargeRemoteDataSource,
    private val rechargeDao: RechargeDao
) {

    suspend fun addRecharge(
        rechargeDto: RechargeDto
    ): Result<String, String> {
        val result = rechargeRemoteDataSource.addRecharge(rechargeDto)

        if(result is Result.Failure) {
            Logger.log(result.error.cleanString())
            return Result.Failure(result.error.cleanString())
        }

        val rechargeDto = (result as Result.Success).data

        val rechargeEntity = RechargeEntityMapper.map(rechargeDto)

        rechargeDao.upsertRecharge(rechargeEntity)

        return Result.Success(rechargeDto.id)
    }

    fun observeRecharges(
    ): Flow<List<Recharge>> {
        return rechargeDao.observeRecharges().map { rechargeEntityList ->
            RechargeMapper.map(rechargeEntityList)
        }
    }

    //todo error propagation
    suspend fun getAllRecharges(

    ) {
        val result = rechargeRemoteDataSource.getAllRecharges()

        if(result is Result.Failure) {
            Logger.log(result.error.cleanString())
        }

        val rechargeList = (result as Result.Success).data

        val rechargeEntityList = RechargeEntityMapper.map(rechargeList)

        Logger.log("RechargeEntityList2", rechargeEntityList.toString())

        rechargeDao.upsertRecharges(rechargeEntityList)
    }

    suspend fun queryDatabase() {
        val rechargeEntityList2 = rechargeDao.getAllRecharges()
        Logger.log("RechargeEntityList", rechargeEntityList2.toString())
    }

}