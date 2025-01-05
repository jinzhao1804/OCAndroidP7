package com.openclassrooms.arista

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository
    private lateinit var mockUserDao: UserDtoDao

    @Before
    fun setUp() {
        mockUserDao = mock(UserDtoDao::class.java)
        userRepository = UserRepository(mockUserDao)
    }

    @Test
    fun `getUser should return Flow of UserDto`() = runTest {
        // Arrange
        val userDto = UserDto(id = 1, name = "John Doe", email = "john.doe@example.com")
        `when`(mockUserDao.getCurrentUser()).thenReturn(flowOf(userDto))

        // Act
        val result = userRepository.getUser().first()

        // Assert
        assertEquals(userDto, result)
        verify(mockUserDao).getCurrentUser()
    }

    @Test
    fun `setUser should call insertUser on UserDtoDao`() = runTest {
        // Arrange
        val user = User(name = "Jane Doe", email = "jane.doe@example.com")
        val userDto = user.toDto()

        // Act
        userRepository.setUser(user)

        // Assert
        verify(mockUserDao).insertUser(userDto)
    }
}