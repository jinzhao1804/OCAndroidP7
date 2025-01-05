package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first
import javax.inject.Inject

open class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDtoDao) {

    // Get all exercises
    suspend fun getAllExercises(): List<Exercise> {
        return try {
            exerciseDao.getAllExercises()
                .first() // Collect the first emission of the Flow
                .map { Exercise.fromDto(it) } // Convert every DTO in Exercise
        } catch (e: Exception) {
            // Handle the error: log it, or return a default value, or rethrow
            throw Exception("Error fetching exercises: ${e.message}", e)
        }
    }

    // Add a new exercise
    suspend fun addExercise(exercise: Exercise) {
        try {
            exerciseDao.insertExercise(exercise.toDto())
        } catch (e: Exception) {
            // Handle the error: log it, or return a default value, or rethrow
            throw Exception("Error adding exercise: ${e.message}", e)
        }
    }

    // Delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {
        try {
            exercise.id?.let {
                exerciseDao.deleteExerciseById(id = it)
            } ?: throw IllegalArgumentException("Exercise ID is null.")
        } catch (e: Exception) {
            // Handle the error: log it, or return a default value, or rethrow
            throw Exception("Error deleting exercise: ${e.message}", e)
        }
    }
}
