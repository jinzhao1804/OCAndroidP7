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

class AddNewExerciseUseCaseTest {

    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var mockExerciseDao: ExerciseDtoDao

    @Before
    fun setUp() {
        mockExerciseDao = mock(ExerciseDtoDao::class.java)
        exerciseRepository = ExerciseRepository(mockExerciseDao)
    }



    @Test
    fun `addExercise should call dao insertExercise`() = runTest {
        // Arrange
        val startTime = LocalDateTime.now()
        val exercise = Exercise(
            id = 1,
            startTime = startTime,
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )
        val exerciseDto = ExerciseDto(
            id = 1,
            startTime = startTime.toEpochSecond(ZoneOffset.UTC) * 1000, // Convert to milliseconds
            duration = 30,
            category = "Running",
            intensity = 5
        )

        // Mock the DAO to return a value when insertExercise is called
        `when`(mockExerciseDao.insertExercise(anyOrNull())).thenReturn(1L) // Assuming it returns a Long

        // Act
        exerciseRepository.addExercise(exercise)

        // Assert
        verify(mockExerciseDao).insertExercise(exerciseDto)
    }

    @Test(expected = Exception::class)
    fun `addExercise should throw exception when dao throws exception`() = runTest {
        // Arrange
        val exercise = Exercise(null, LocalDateTime.now(), 30, ExerciseCategory.Running, 5)
        `when`(mockExerciseDao.insertExercise(any())).thenThrow(RuntimeException("Database error"))

        // Act
        exerciseRepository.addExercise(exercise)

        // Assert is handled by the expected exception
    }

}