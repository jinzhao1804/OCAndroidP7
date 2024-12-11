package com.openclassrooms.arista


import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
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

@RunWith(MockitoJUnitRunner::class) // This is the annotation to run with JUnit4 and use Mockito
class GetAllExercisesUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initializes mock objects
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks() // Clean up after tests
    }

    @Test
    fun `when repository returns exercises, use case should return them`() = runBlocking {
        // Arrange: Define the fake data to be returned by the repository
        val fakeExercises = listOf(
            Exercise(
                startTime = java.time.LocalDateTime.now(),
                duration = 30,
                category = ExerciseCategory.Running,
                intensity = 5
            ),
            Exercise(
                startTime = java.time.LocalDateTime.now().plusHours(1),
                duration = 45,
                category = ExerciseCategory.Riding,
                intensity = 7
            )
        )
        // Mocking the repository to return the fake data
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)

        // Act: Call the use case's execute method
        val result = getAllExercisesUseCase.execute()

        // Assert: Verify that the result is the same as the fake data
        assertEquals(fakeExercises, result)
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange: Mock the repository to return an empty list
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())

        // Act: Call the use case's execute method
        val result = getAllExercisesUseCase.execute()

        // Assert: Verify that the result is an empty list
        assertTrue(result.isEmpty())
    }
}
