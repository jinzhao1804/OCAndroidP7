package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var mockExerciseRepository: ExerciseRepository

    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(mockExerciseRepository)
    }

    @Test
    fun `execute should call addExercise on repository`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = null,
            startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        // Act
        addNewExerciseUseCase.execute(exercise)

        // Assert
        verify(mockExerciseRepository, times(1)).addExercise(exercise)
    }

    @Test(expected = Exception::class)
    fun `execute should throw exception when repository throws exception`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = null,
            startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        val exceptionMessage = "Failed to add exercise"
        doThrow(Exception(exceptionMessage)).`when`(mockExerciseRepository).addExercise(exercise)

        // Act
        try {
            addNewExerciseUseCase.execute(exercise)
        } catch (e: Exception) {
            // Assert
            assertEquals(exceptionMessage, e.message)
            throw e
        }
    }
}