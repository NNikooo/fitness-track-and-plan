package com.example.fitnesstrackandplan.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user_data_records")
data class UserDataRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fieldName: String,
    val value: String,
    val date: LocalDate
)