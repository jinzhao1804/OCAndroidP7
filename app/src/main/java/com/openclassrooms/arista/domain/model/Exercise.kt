package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory, // Assuming ExerciseCategory is an enum
    var intensity: Int
) {
    // Convert DTO to Domain model
    companion object {
        fun fromDto(dto: ExerciseDto): Exercise {
            return Exercise(
                id = dto.id,
                startTime = LocalDateTime.ofEpochSecond(dto.startTime / 1000, 0, ZoneOffset.UTC), // Convert Long (milliseconds) to LocalDateTime
                duration = dto.duration,
                category = ExerciseCategory.valueOf(dto.category), // Assuming ExerciseCategory is an enum
                intensity = dto.intensity
            )
        }
    }

    // Convert Domain model to DTO
    fun toDto(): ExerciseDto {
        return ExerciseDto(
            id = id,
            startTime = startTime.toEpochSecond(ZoneOffset.UTC) * 1000, // Convert LocalDateTime to Long (milliseconds)
            duration = duration,
            category = category.name, // Assuming ExerciseCategory is an enum
            intensity = intensity
        )
    }
}
