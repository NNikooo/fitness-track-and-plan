package com.example.fitnesstrackandplan.utils

import com.example.fitnesstrackandplan.model.TrainingUnit

fun totalTrainings(trainings: List<TrainingUnit>): Int {
    return trainings.size
}

fun finishedTrainings(trainings: List<TrainingUnit>): Int {
    return trainings.count { it.completed }
}

fun plannedTrainings(trainings: List<TrainingUnit>) : Int {
    return trainings.count {!it.completed}
}

fun completionRate(trainings: List<TrainingUnit>): Int {
    return if (trainings.isNotEmpty()) {
        finishedTrainings(trainings) * 100 / trainings.size
    } else {
        0
    }
}

fun totalDuration(trainings: List<TrainingUnit>): Int {
    return trainings.sumOf { it.duration }
}

fun averageDuration(trainings: List<TrainingUnit>): Int {
    return if (trainings.isNotEmpty()) {
        totalDuration(trainings) / trainings.size
    } else {
        0
    }
}

fun mostCommonType(trainings: List<TrainingUnit>): String {
    return trainings
        .groupingBy { it.type }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key ?: "No data"
}