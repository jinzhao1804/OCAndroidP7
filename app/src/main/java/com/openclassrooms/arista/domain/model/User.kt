package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.UserDto

data class User(
    var name: String,
    var email: String
) {
    // Convert UserDto to User (Domain Model)
    companion object {
        fun fromDto(dto: UserDto): User {
            return User(
                name = dto.name,
                email = dto.email
            )
        }
    }

    // Convert User (Domain Model) to UserDto
    fun toDto(): UserDto {
        return UserDto(
            name = name,
            email = email
        )
    }

}
