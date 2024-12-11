package com.openclassrooms.arista

import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.data.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)  // Initialize mocks
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()  // Clean up after tests
    }

    @Test
    fun `when execute is called, repository should add exercise`() = runBlocking {
        // Arrange: Prepare the exercise data
        val exercise = Exercise(
            startTime = java.time.LocalDateTime.now(),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        // Act: Call the use case's execute method
        addNewExerciseUseCase.execute(exercise)

        // Assert: Verify that addExercise was called with the correct exercise
        Mockito.verify(exerciseRepository).addExercise(exercise)  // Verify that addExercise was called with the correct exercise
    }

    @Test
    fun `when add exercise fails, use case should handle error`() = runBlocking {
        // Arrange: Prepare the exercise data
        val exercise = Exercise(
            startTime = java.time.LocalDateTime.now(),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )

        // Mock the repository to throw an exception when addExercise is called
        Mockito.`when`(exerciseRepository.addExercise(exercise)).thenThrow(RuntimeException("Failed to add exercise"))

        try {
            // Act: Call the use case's execute method, which should throw an exception
            addNewExerciseUseCase.execute(exercise)
            fail("Exception was expected, but it was not thrown.")
        } catch (e: Exception) {
            // Assert: Verify that the exception is correctly handled
            assertTrue(e is RuntimeException)
            assertEquals("Failed to add exercise", e.message)
        }
    }
}
