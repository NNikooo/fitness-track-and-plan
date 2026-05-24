package com.example.fitnesstrackandplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.cards.TrainingCard
import com.example.fitnesstrackandplan.ui.theme.BackgroundCol
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun HomeScreen(
    trainings: List<TrainingUnit>,
    onAddClick: () -> Unit,
    onTrainingClick: (Int) -> Unit
) {
    val trainingsCount = trainings.size
    val hour = LocalTime.now().hour
    val greeting = when{
        hour < 12 -> "Good morning"
        hour < 18 -> "Good afternoon"
        else -> "Good evening"
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Training"
                )
            }
        }
    ) {
        paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Fitness Track & Plan",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "$greeting, here is your tranings overview ;-)",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Number of trainings: $trainingsCount")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Trainings",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (trainings.isEmpty()) {
                Text(
                    text = "No trainings found",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    trainings.forEach { trainingUnit ->
                        TrainingCard(
                            trainingUnit = trainingUnit,
                            onClick = { onTrainingClick(trainingUnit.id) }
                        )
                    }
                }
            }
        }
    }
}