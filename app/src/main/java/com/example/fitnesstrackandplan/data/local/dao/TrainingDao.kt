package com.example.fitnesstrackandplan.data.local.dao

import androidx.room.*
import com.example.fitnesstrackandplan.model.TrainingUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training_units ORDER BY id DESC")
    fun getAllTrainings(): Flow<List<TrainingUnit>>

    @Insert
    suspend fun insertTraining(training: TrainingUnit)

    @Update
    suspend fun updateTraining(training: TrainingUnit)

    @Delete
    suspend fun deleteTraining(training: TrainingUnit)
}