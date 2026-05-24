package com.example.fitnesstrackandplan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String,
    val age: Int,
    val height: Int,
    val weight: Double
)