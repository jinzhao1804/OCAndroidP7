package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class DeleteExerciseUseCaseTest {

    @Mock
    private lateinit var mockExerciseRepository: ExerciseRepository

    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deleteExerciseUseCase = DeleteExerciseUseCase(mockExerciseRepository)
    }

    @Test
    fun `execute should call deleteExercise on repository`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = 1,
            startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        // Act
        deleteExerciseUseCase.execute(exercise)

        // Assert
        verify(mockExerciseRepository, times(1)).deleteExercise(exercise)
    }

    @Test(expected = Exception::class)
    fun `execute should throw exception when repository throws exception`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = 1,
            startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        val exceptionMessage = "Error deleting exercise"
        doThrow(Exception(exceptionMessage)).`when`(mockExerciseRepository).deleteExercise(exercise)

        // Act
        try {
            deleteExerciseUseCase.execute(exercise)
        } catch (e: Exception) {
            // Assert
            assertEquals(exceptionMessage, e.message)
            throw e
        }
    }

    @Test
    fun `execute should throw IllegalArgumentException when exercise ID is null`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = null, // ID is null
            startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        // Mock the repository to throw IllegalArgumentException
        doThrow(IllegalArgumentException("Exercise ID is null."))
            .`when`(mockExerciseRepository)
            .deleteExercise(exercise)

        // Act and Assert
        val exception = assertThrows(Exception::class.java) {
            runBlocking {
                deleteExerciseUseCase.execute(exercise)
            }
        }

        // Verify the exception message
        assertEquals("Error deleting exercise: Exercise ID is null.", exception.message)
    }
}