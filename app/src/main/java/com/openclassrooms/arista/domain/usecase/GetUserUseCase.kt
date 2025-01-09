package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(): Flow<UserDto> {
        return try {
            // Attempt to get the user from the repository
            userRepository.getUser()
        } catch (e: Exception) {
            // Handle the error: log it, or throw a custom exception
            throw Exception("Error fetching user: ${e.message}", e)
        }
    }
}
