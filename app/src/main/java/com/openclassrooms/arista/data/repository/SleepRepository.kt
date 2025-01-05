package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class SleepRepository @Inject constructor (private val sleepDao: SleepDtoDao) {

    fun getAllSleeps(): Flow<List<Sleep>> {
        return sleepDao.getAllSleeps()
            .map { sleepDtoList -> sleepDtoList.map { Sleep.fromDto(it) } }
    }
}