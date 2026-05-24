package com.example.fitnesstrackandplan.utils

import com.example.fitnesstrackandplan.model.TrainingUnit
import java.time.LocalDate

fun filterTrainingsByDateRange(
    trainings: List<TrainingUnit>,
    startDate: LocalDate?,
    endDate: LocalDate?
): List<TrainingUnit> {
    return trainings.filter { training ->
        (startDate == null || !training.date.isBefore(startDate)) &&
                (endDate == null || !training.date.isAfter(endDate))
    }
}