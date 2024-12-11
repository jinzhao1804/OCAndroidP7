package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    suspend fun execute(exercise: Exercise) {
        try {
            // Attempt to delete the exercise through the repository
            exerciseRepository.deleteExercise(exercise)
        } catch (e: Exception) {
            // Handle the error: log it, or throw a custom exception
            throw Exception("Error deleting exercise: ${e.message}", e)
        }
    }
}
