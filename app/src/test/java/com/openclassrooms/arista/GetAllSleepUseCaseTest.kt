package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class GetAllSleepsUseCaseTest {

    @Mock
    private lateinit var mockSleepRepository: SleepRepository

    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(mockSleepRepository)
    }

    @Test
    fun `execute should return list of sleeps when repository succeeds`(): Unit = runBlocking {
        // Arrange
        val sleepList = listOf(
            Sleep(
                startTime = LocalDateTime.of(2023, 10, 1, 22, 0),
                duration = 480,
                quality = 4
            ),
            Sleep(
                startTime = LocalDateTime.of(2023, 10, 2, 23, 0),
                duration = 360,
                quality = 3
            )
        )

        // Mock the repository to return the list of sleeps
        `when`(mockSleepRepository.getAllSleeps()).thenReturn(flowOf(sleepList))

        // Act
        val result = getAllSleepsUseCase.execute()

        // Assert
        assertEquals(sleepList, result)
        verify(mockSleepRepository, times(1)).getAllSleeps()
    }

    @Test(expected = Exception::class)
    fun `execute should throw exception when repository throws exception`(): Unit = runBlocking {
        // Arrange
        val exceptionMessage = "Error fetching sleeps"
        `when`(mockSleepRepository.getAllSleeps()).thenThrow(Exception(exceptionMessage))

        // Act
        try {
            getAllSleepsUseCase.execute()
        } catch (e: Exception) {
            // Assert
            assertEquals(exceptionMessage, e.message)
            throw e
        }
    }
}