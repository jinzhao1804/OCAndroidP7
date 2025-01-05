package com.openclassrooms.arista

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import java.time.LocalDateTime
import java.time.ZoneOffset

class DeleteExerciseUseCaseTest {

    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var mockExerciseDao: ExerciseDtoDao

    @Before
    fun setUp() {
        mockExerciseDao = mock(ExerciseDtoDao::class.java)
        exerciseRepository = ExerciseRepository(mockExerciseDao)
    }



    @Test
    fun `deleteExercise should call dao deleteExerciseById`() = runTest {
        // Arrange
        val exercise = Exercise(1, LocalDateTime.now(), 30, ExerciseCategory.Running, 5)

        // Act
        exerciseRepository.deleteExercise(exercise)

        // Assert
        verify(mockExerciseDao).deleteExerciseById(1)
    }

    @Test(expected = Exception::class)
    fun `deleteExercise should throw exception when exercise id is null`() = runTest {
        // Arrange
        val exercise = Exercise(null, LocalDateTime.now(), 30, ExerciseCategory.Running, 5)

        // Act
        exerciseRepository.deleteExercise(exercise)

        // Assert is handled by the expected exception
    }

    @Test(expected = Exception::class)
    fun `deleteExercise should throw exception when dao throws exception`() = runTest {
        // Arrange
        val exercise = Exercise(1, LocalDateTime.now(), 30, ExerciseCategory.Running, 5)
        `when`(mockExerciseDao.deleteExerciseById(any())).thenThrow(RuntimeException("Database error"))

        // Act
        exerciseRepository.deleteExercise(exercise)

        // Assert is handled by the expected exception
    }
}