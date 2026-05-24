package com.example.fitnesstrackandplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.TrainingExercise
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.theme.ButtonBlue
import com.example.fitnesstrackandplan.ui.theme.ButtonGreen
import com.example.fitnesstrackandplan.ui.theme.ButtonRed
import com.example.fitnesstrackandplan.ui.theme.ExerciseCard
import com.example.fitnesstrackandplan.ui.theme.TextCol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailScreen(
    trainingUnit: TrainingUnit?,
    onBackClick: () -> Unit,
    onFinishClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (TrainingUnit) -> Unit,
    onAddExerciseClick: (Int) -> Unit,
    exercises: List<TrainingExercise>,
    onDeleteExerciseClick: (TrainingExercise) -> Unit,
    onEditExerciseClick: (Int) -> Unit
) {
    if (trainingUnit == null) {
        Text("Training not found")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Training detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = trainingUnit.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Type: ${trainingUnit.type}")
                    Text("Date: ${trainingUnit.date}")
                    Text("Time: ${trainingUnit.time}")
                    Text("Duration: ${trainingUnit.duration} min")
                    Text(
                        text = if (trainingUnit.completed)
                            "Status: finished"
                        else
                            "Status: planned"
                    )
                    Text(
                        text = "Exercises",
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (exercises.isEmpty()) {
                        Text("No exercises added yet.")
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(exercises) { exercise ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = ExerciseCard
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = exercise.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text("Sets: ${exercise.sets}")
                                        Text("Reps: ${exercise.reps}")
                                        Text("Pause: ${exercise.pause}")
                                        Text("RPE: ${exercise.rpe}")
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Button(
                                                colors = buttonColors(
                                                    containerColor = ButtonBlue,
                                                    contentColor = TextCol
                                                ),
                                                onClick = { onEditExerciseClick(exercise.id) },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Edit exercise")
                                            }

                                            Button(
                                                colors = buttonColors(
                                                    containerColor = ButtonRed,
                                                    contentColor = TextCol
                                                ),
                                                onClick = { onDeleteExerciseClick(exercise) },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Delete exercise")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Button(
                colors = buttonColors(
                    containerColor = ButtonGreen,
                    contentColor = TextCol
                ),
                onClick = { onFinishClick(trainingUnit.id) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !trainingUnit.completed
            ) {
                Text("Mark as finished")
            }

            Button(
                colors = buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = TextCol
                ),
                onClick = {
                    onEditClick(trainingUnit.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit training")
            }

            Button(
                colors = buttonColors(
                    containerColor = ButtonRed,
                    contentColor = TextCol
                ),
                onClick = {
                    onDeleteClick(trainingUnit)
                    onBackClick()
                          },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Delete training")
            }

            Button(
                colors = buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = TextCol
                ),
                onClick = { onAddExerciseClick(trainingUnit.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add exercise")
            }

        }
    }
}