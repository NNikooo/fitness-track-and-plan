package com.example.fitnesstrackandplan.model
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "training_exercises",
    foreignKeys = [
        ForeignKey(
            entity = TrainingUnit::class,
            parentColumns = ["id"],
            childColumns = ["trainingUnitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrainingExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val trainingUnitId: Int,

    val title: String,
    val sets: Int,
    val reps: Int,
    val pause: String,
    val rpe: Int
)