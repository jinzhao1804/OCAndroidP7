package com.openclassrooms.arista

import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import com.openclassrooms.arista.data.repository.SleepRepository
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
import java.time.LocalDateTime
import java.time.ZoneOffset

@RunWith(MockitoJUnitRunner::class)
class GetAllSleepUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository

    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initializes mock objects
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks() // Clean up after tests
    }

    @Test
    fun `when repository returns sleeps, use case should return them`() = runBlocking {
        // Arrange: Define the fake data to be returned by the repository
        val fakeSleeps = listOf(
            Sleep(
                startTime = LocalDateTime.now().minusDays(1),
                duration = 480,
                quality = 4
            ),
            Sleep(
                startTime = LocalDateTime.now().minusDays(2),
                duration = 450,
                quality = 3
            ),
            Sleep(
                startTime = LocalDateTime.now().minusDays(2),
                duration = 450,
                quality = 3
            )
        )

        // Mocking the repository to return the fake data
        Mockito.`when`(sleepRepository.getAllSleeps()).thenReturn(fakeSleeps)

        // Act: Call the use case's execute method
        val result = getAllSleepsUseCase.execute()

        // Assert: Verify that the result is the same as the fake data
        assertEquals(fakeSleeps.size, result.size)
        for (i in fakeSleeps.indices) {
            assertEquals(fakeSleeps[i].startTime, result[i].startTime)
            assertEquals(fakeSleeps[i].duration, result[i].duration)
            assertEquals(fakeSleeps[i].quality, result[i].quality)
        }
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange: Mock the repository to return an empty list
        Mockito.`when`(sleepRepository.getAllSleeps()).thenReturn(emptyList())

        // Act: Call the use case's execute method
        val result = getAllSleepsUseCase.execute()

        // Assert: Verify that the result is an empty list
        assertTrue(result.isEmpty())
    }
}
