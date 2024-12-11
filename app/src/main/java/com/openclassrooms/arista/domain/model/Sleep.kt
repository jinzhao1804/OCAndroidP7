package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Sleep(
    @JvmField var startTime: LocalDateTime,  // LocalDateTime is used for start time
    var duration: Int,  // Duration in minutes
    var quality: Int    // Quality scale (1-5, for example)
) {
    // Convert SleepDto to Sleep (Domain Model)
    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                startTime = LocalDateTime.ofEpochSecond(dto.startTime / 1000, 0, ZoneOffset.UTC), // Convert milliseconds to LocalDateTime
                duration = dto.duration,
                quality = dto.quality
            )
        }
    }

    // Convert Sleep (Domain Model) to SleepDto
    fun toDto(): SleepDto {
        return SleepDto(
            startTime = startTime.toEpochSecond(ZoneOffset.UTC) * 1000, // Convert LocalDateTime to milliseconds
            duration = duration,
            quality = quality
        )
    }
}
