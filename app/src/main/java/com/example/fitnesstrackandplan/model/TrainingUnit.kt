package com.example.fitnesstrackandplan.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "training_units")
data class TrainingUnit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val type: String,
    val date: LocalDate,
    val time: LocalTime,
    val duration: Int,
    val completed: Boolean = false
)