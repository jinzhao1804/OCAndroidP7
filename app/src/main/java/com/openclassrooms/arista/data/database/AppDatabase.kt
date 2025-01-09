package com.openclassrooms.arista.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.ExerciseCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)  // Register the TypeConverters

abstract class AppDatabase : RoomDatabase() {

    // DAOs to access the database tables
    abstract fun userDtoDao(): UserDtoDao
    abstract fun sleepDtoDao(): SleepDtoDao
    abstract fun exerciseDtoDao(): ExerciseDtoDao

    // Callback to handle initial database population when it is created
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // Populate the database with initial data
                    populateDatabase(database.sleepDtoDao(), database.exerciseDtoDao(), database.userDtoDao() )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Get the singleton instance of the database
        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"  // Name of the database
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))  // Adding the callback for initial population
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Function to populate the database with initial data
        suspend fun populateDatabase(
            sleepDao: SleepDtoDao,
            exerciseDtoDao: ExerciseDtoDao,
            userDtoDao: UserDtoDao
        ) {
            // Populate Sleep data
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 480,
                    quality = 4
                )
            )

            Log.e("Database", "Sleep data inserted")

            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 450,
                    quality = 3
                )
            )
            Log.e("Database", "Sleep data inserted")

            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 450,
                    quality = 3
                )
            )
            Log.e("Database", "Sleep data inserted")

            // You can populate other tables (like ExerciseDto or UserDto) in a similar way if needed
            exerciseDtoDao.insertExercise(
                ExerciseDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
                    duration = 30,
                    category = ExerciseCategory.Walking.toString(),
                    intensity = 6
                )
            )
            Log.e("Database", "Exercise data inserted: Walking")


            userDtoDao.insertUser(
                UserDto(
                    id = 1,
                    name = "John Doe",
                    email = "example@email.com"
                )
            )
            Log.e("Database", "User data inserted")

        }
    }
}
