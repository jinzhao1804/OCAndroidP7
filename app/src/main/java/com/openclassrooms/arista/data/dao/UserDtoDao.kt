package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDtoDao {


    @Query("SELECT * FROM user")
    fun getCurrentUser(): Flow<UserDto>

    @Insert
    suspend fun insertUser(user: UserDto): Long

}