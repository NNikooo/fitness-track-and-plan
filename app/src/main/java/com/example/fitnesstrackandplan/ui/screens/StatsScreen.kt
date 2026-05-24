package com.example.fitnesstrackandplan.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.theme.ExerciseCard
import com.example.fitnesstrackandplan.utils.averageDuration
import com.example.fitnesstrackandplan.utils.completionRate
import com.example.fitnesstrackandplan.utils.filterTrainingsByDateRange
import com.example.fitnesstrackandplan.utils.finishedTrainings
import com.example.fitnesstrackandplan.utils.mostCommonType
import com.example.fitnesstrackandplan.utils.plannedTrainings
import com.example.fitnesstrackandplan.utils.totalDuration
import com.example.fitnesstrackandplan.utils.totalTrainings
import java.time.LocalDate
import java.util.Calendar

@Composable
fun StatsScreen(
    trainings: List<TrainingUnit>
) {
    var fromDate by rememberSaveable { mutableStateOf("") }
    var toDate by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val startDatePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fromDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    val endDatePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            toDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val start = fromDate.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) }
    val end = toDate.takeIf { it.isNotBlank() }?.let { LocalDate.parse(it) }

    val filteredTrainings = filterTrainingsByDateRange(
        trainings = trainings,
        startDate = start,
        endDate = end
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Your training overview",
            style = MaterialTheme.typography.bodyLarge
        )

        StatCard(
            title = "Total trainings",
            lines = listOf(
                "Total trainings: ${totalTrainings(trainings)}",
                "Finished trainings: ${finishedTrainings(trainings)}",
                "Planned trainings: ${plannedTrainings(trainings)}",
                "Completion rate: ${completionRate(trainings)}%",
                "Total duration: ${totalDuration(filteredTrainings)}",
                "Average duration: ${averageDuration(trainings)} min"
            )
        )

        StatCard(
            title = if (fromDate.isBlank() && toDate.isBlank()) {
                "All trainings stats"
            } else if (fromDate.isBlank() && !toDate.isBlank()) {
                "Trainings from the first one to $toDate"
            } else if (!fromDate.isBlank() && toDate.isBlank()) {
                "Trainings from $fromDate to the last one"
            } else {
                "Trainings from $fromDate to $toDate"
            },
            lines = listOf(
                "Total trainings: ${totalTrainings(filteredTrainings)}",
                "Finished trainings: ${finishedTrainings(filteredTrainings)}",
                "Planned trainings: ${plannedTrainings(filteredTrainings)}",
                "Completion rate: ${completionRate(filteredTrainings)}%",
                "Total duration: ${totalDuration(filteredTrainings)}",
                "Average duration: ${averageDuration(filteredTrainings)} min"

            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { startDatePicker.show() },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (fromDate.isBlank()) "Start date" else fromDate)
            }

            Button(
                onClick = {
                    fromDate = ""
                    toDate = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear filter")
            }

            Button(
                onClick = { endDatePicker.show() },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (toDate.isBlank()) "End date" else toDate)
            }
        }

        StatCard(
            title = "Most common type",
            lines = listOf(
                mostCommonType(trainings)
            )
        )
    }
}

@Composable
fun StatCard(
    title: String,
    lines: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ExerciseCard
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            lines.forEach { line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}