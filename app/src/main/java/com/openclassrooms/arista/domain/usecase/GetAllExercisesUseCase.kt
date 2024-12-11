package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject


class GetAllExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    suspend fun execute(): List<Exercise> {
        return try {
            // Try to fetch all exercises from the repository
            exerciseRepository.getAllExercises()
        } catch (e: Exception) {
            // Handle the error (e.g., log it or rethrow as a custom exception)
            throw Exception("Error fetching exercises: ${e.message}", e)
        }
    }
}
