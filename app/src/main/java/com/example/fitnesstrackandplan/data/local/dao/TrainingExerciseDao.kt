package com.example.fitnesstrackandplan.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fitnesstrackandplan.model.TrainingExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingExerciseDao {
    @Query("SELECT * FROM training_exercises WHERE trainingUnitId = :trainingUnitId")
    fun getExercisesForTraining(trainingUnitId: Int): Flow<List<TrainingExercise>>
    @Insert
    suspend fun insertExercise(exercise: TrainingExercise)
    @Update
    suspend fun updateExercise(exercise: TrainingExercise)
    @Delete
    suspend fun deleteExercise(exercise: TrainingExercise)
}