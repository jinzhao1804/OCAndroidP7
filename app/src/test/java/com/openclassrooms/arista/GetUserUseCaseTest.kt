package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.flow
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
class GetUserUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var getUserUseCase: GetUserUsecase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initializes mock objects
        getUserUseCase = GetUserUsecase(userRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks() // Clean up after tests
    }

    @Test
    fun `when repository returns user, use case should return the user`() = runBlocking {
        // Arrange: Prepare the fake UserDto to be returned by the repository
        val fakeUserDto = UserDto(
            name = "John Doe",
            email = "john.doe@example.com"
        )

        // Mocking the repository to return the flow with the fake user data
        Mockito.`when`(userRepository.getUser()).thenReturn(flow { emit(fakeUserDto) })

        // Act: Call the use case's execute method
        val result = getUserUseCase.execute()

        // Assert: Verify that the result contains the expected UserDto
        result.collect { userDto ->
            assertEquals(fakeUserDto.name, userDto.name)
            assertEquals(fakeUserDto.email, userDto.email)
        }
    }

    @Test
    fun `when repository returns empty user, use case should return empty user`() = runBlocking {
        // Arrange: Prepare the empty UserDto to be returned by the repository
        val emptyUserDto = UserDto(name = "", email = "")

        // Mocking the repository to return the flow with the empty user data
        Mockito.`when`(userRepository.getUser()).thenReturn(flow { emit(emptyUserDto) })

        // Act: Call the use case's execute method
        val result = getUserUseCase.execute()

        // Assert: Verify that the result contains the expected empty UserDto
        result.collect { userDto ->
            assertEquals(emptyUserDto.name, userDto.name)
            assertEquals(emptyUserDto.email, userDto.email)
        }
    }
}
