package com.openclassrooms.arista

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetUserUsecaseTest {

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private lateinit var getUserUsecase: GetUserUsecase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getUserUsecase = GetUserUsecase(mockUserRepository)
    }

    @Test
    fun `execute should return user flow when repository succeeds`(): Unit = runBlocking {
        // Arrange
        val userDto = UserDto(
            id = 1,
            name = "John Doe",
            email = "john.doe@example.com"
        )
        val userFlow = flowOf(userDto)

        // Mock the repository to return the user flow
        `when`(mockUserRepository.getUser()).thenReturn(userFlow)

        // Act
        val result = getUserUsecase.execute().toList()

        // Assert
        assertEquals(listOf(userDto), result)
        verify(mockUserRepository, times(1)).getUser()
    }

    @Test(expected = Exception::class)
    fun `execute should throw exception when repository throws exception`(): Unit = runBlocking {
        // Arrange
        val exceptionMessage = "Error fetching user"
        `when`(mockUserRepository.getUser()).thenThrow(Exception(exceptionMessage))

        // Act
        try {
            getUserUsecase.execute().toList()
        } catch (e: Exception) {
            // Assert
            assertEquals(exceptionMessage, e.message)
            throw e
        }
    }
}