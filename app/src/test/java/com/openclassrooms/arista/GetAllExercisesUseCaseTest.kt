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

class GetAllExercisesUseCaseTest {

    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var mockExerciseDao: ExerciseDtoDao

    @Before
    fun setUp() {
        mockExerciseDao = mock(ExerciseDtoDao::class.java)
        exerciseRepository = ExerciseRepository(mockExerciseDao)
    }

    @Test
    fun `getAllExercises should return list of exercises`() = runTest {
        // Arrange
        val exerciseDtoList = listOf(
            ExerciseDto(1, 1633072800000, 30, "Running", 5),
            ExerciseDto(2, 1633159200000, 45, "Swimming", 7)
        )
        val expectedExerciseList = listOf(
            Exercise(
                1,
                LocalDateTime.ofEpochSecond(1633072800, 0, ZoneOffset.UTC),
                30,
                ExerciseCategory.Running,
                5
            ),
            Exercise(
                2,
                LocalDateTime.ofEpochSecond(1633159200, 0, ZoneOffset.UTC),
                45,
                ExerciseCategory.Swimming,
                7
            )
        )

        `when`(mockExerciseDao.getAllExercises()).thenReturn(flowOf(exerciseDtoList))

        // Act
        val result = exerciseRepository.getAllExercises()

        // Assert
        assertEquals(expectedExerciseList, result)
        verify(mockExerciseDao).getAllExercises()
    }

    @Test(expected = Exception::class)
    fun `getAllExercises should throw exception when dao throws exception`() = runTest {
        // Arrange
        `when`(mockExerciseDao.getAllExercises()).thenThrow(RuntimeException("Database error"))

        // Act
        exerciseRepository.getAllExercises()

        // Assert is handled by the expected exception
    }



}
