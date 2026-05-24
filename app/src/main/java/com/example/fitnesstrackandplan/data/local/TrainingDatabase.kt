package com.example.fitnesstrackandplan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnesstrackandplan.data.local.dao.TrainingDao
import com.example.fitnesstrackandplan.data.local.dao.TrainingExerciseDao
import com.example.fitnesstrackandplan.data.local.dao.UserDataRecordDao
import com.example.fitnesstrackandplan.data.local.dao.UserProfileDao
import com.example.fitnesstrackandplan.model.TrainingExercise
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.model.UserDataRecord
import com.example.fitnesstrackandplan.model.UserProfile

@Database(
    entities = [TrainingUnit::class, TrainingExercise::class, UserProfile::class, UserDataRecord::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TrainingDatabase : RoomDatabase() {

    abstract fun trainingUnitDao(): TrainingDao
    abstract fun trainingExerciseDao(): TrainingExerciseDao

    abstract fun userProfileDao() : UserProfileDao

    abstract fun userDataRecordDao(): UserDataRecordDao

    companion object {
        @Volatile
        private var INSTANCE: TrainingDatabase? = null

        fun getDatabase(context: Context): TrainingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrainingDatabase::class.java,
                    "training_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}