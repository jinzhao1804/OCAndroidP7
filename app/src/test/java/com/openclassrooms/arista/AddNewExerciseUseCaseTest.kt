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

class ExerciseRepositoryTest {

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
            Exercise(1, LocalDateTime.ofEpochSecond(1633072800, 0, ZoneOffset.UTC), 30, ExerciseCategory.Running, 5),
            Exercise(2, LocalDateTime.ofEpochSecond(1633159200, 0, ZoneOffset.UTC), 45, ExerciseCategory.Swimming, 7)
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