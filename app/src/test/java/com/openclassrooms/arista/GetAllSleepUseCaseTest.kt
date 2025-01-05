package com.openclassrooms.arista

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class SleepRepositoryTest {

    private lateinit var sleepRepository: SleepRepository
    private lateinit var mockSleepDao: SleepDtoDao

    @Before
    fun setUp() {
        mockSleepDao = mock(SleepDtoDao::class.java)
        sleepRepository = SleepRepository(mockSleepDao)
    }

    @Test
    fun `getAllSleeps should return list of sleeps`() = runTest {
        // Arrange
        val sleepDtoList = listOf(
            SleepDto(1, 1633072800000, 480, 4),
            SleepDto(2, 1633159200000, 360, 3)
        )
        val expectedSleepList = listOf(
            Sleep(LocalDateTime.ofEpochSecond(1633072800, 0, ZoneOffset.UTC), 480, 4),
            Sleep(LocalDateTime.ofEpochSecond(1633159200, 0, ZoneOffset.UTC), 360, 3)
        )

        `when`(mockSleepDao.getAllSleeps()).thenReturn(flowOf(sleepDtoList))

        // Act
        val result = sleepRepository.getAllSleeps()

        // Assert
        assertEquals(expectedSleepList, result)
        verify(mockSleepDao).getAllSleeps()
    }

    @Test(expected = Exception::class)
    fun `getAllSleeps should throw exception when dao throws exception`() = runTest {
        // Arrange
        `when`(mockSleepDao.getAllSleeps()).thenThrow(RuntimeException("Database error"))

        // Act
        sleepRepository.getAllSleeps()

        // Assert is handled by the expected exception
    }
}