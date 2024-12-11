package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject


class AddNewExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {

    suspend fun execute(exercise: Exercise) {
        try {
            exerciseRepository.addExercise(exercise)
        } catch (e: Exception) {
            // Handle the error, for example, log it or show a message to the user
            throw Exception("Failed to add exercise: ${e.message}", e)
        }
    }
}
