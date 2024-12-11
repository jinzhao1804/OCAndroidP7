package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

class GetAllSleepsUseCase @Inject constructor(private val sleepRepository: SleepRepository) {

    suspend fun execute(): List<Sleep> {
        return try {
            // Attempt to get all sleeps from the repository
            sleepRepository.getAllSleeps()
        } catch (e: Exception) {
            // Handle the error: log it or rethrow with a custom message
            throw Exception("Error fetching sleeps: ${e.message}", e)
        }
    }
}
