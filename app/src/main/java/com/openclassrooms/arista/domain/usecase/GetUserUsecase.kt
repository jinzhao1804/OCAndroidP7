package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(): Flow<UserDto> {
        return userRepository.getUser()
    }
}