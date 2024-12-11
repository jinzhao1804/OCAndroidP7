package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepository @Inject constructor (private val userDao: UserDtoDao) {

    // Get the current user
    suspend fun getUser(): Flow<UserDto> {
        // Fetch the current user DTO from the database and convert it to the User domain model
        return userDao.getCurrentUser()
    }

    // Set or update the user
    suspend fun setUser(user: User) {
        // Convert the User domain model to a UserDto before inserting or updating
        userDao.insertUser(user.toDto())
    }
}
