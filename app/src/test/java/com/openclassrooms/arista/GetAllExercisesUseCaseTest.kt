package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class GetAllExercisesUseCaseTest {

    @Mock
    private lateinit var mockExerciseRepository: ExerciseRepository

    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAllExercisesUseCase = GetAllExercisesUseCase(mockExerciseRepository)
    }

    @Test
    fun `execute should return list of exercises when repository succeeds`(): Unit = runBlocking {
        // Arrange
        val exerciseList = listOf(
            Exercise(
                id = 1,
                startTime = LocalDateTime.of(2023, 10, 1, 10, 0),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            ),
            Exercise(
                id = 2,
                startTime = LocalDateTime.of(2023, 10, 2, 11, 0),
                duration = 45,
                category = ExerciseCategory.Running,
                intensity = 7
            )
        )

        // Mock the repository to return the list of exercises
        `when`(mockExerciseRepository.getAllExercises()).thenReturn(exerciseList)

        // Act
        val result = getAllExercisesUseCase.execute()

        // Assert
        assertEquals(exerciseList, result)
        verify(mockExerciseRepository, times(1)).getAllExercises()
    }

    @Test(expected = Exception::class)
    fun `execute should throw exception when repository throws exception`(): Unit = runBlocking {
        // Arrange
        val exceptionMessage = "Error fetching exercises"
        `when`(mockExerciseRepository.getAllExercises()).thenThrow(Exception(exceptionMessage))

        // Act
        try {
            getAllExercisesUseCase.execute()
        } catch (e: Exception) {
            // Assert
            assertEquals(exceptionMessage, e.message)
            throw e
        }
    }
}