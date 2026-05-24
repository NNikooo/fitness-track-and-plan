package com.example.fitnesstrackandplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.fitnesstrackandplan.ui.forms.ExerciseForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExerciseScreen(
    exercise: TrainingExercise?,
    onBackClick: () -> Unit,
    onSaveClick: (TrainingExercise) -> Unit
) {
    if (exercise == null) {
        Text("Exercise not found")
        return
    }

    ExerciseForm(
        trainingId = exercise.trainingUnitId,
        formTitle = "Edit exercise",
        initExTitle = exercise.title,
        initSets = exercise.sets.toString(),
        initReps = exercise.reps.toString(),
        initPause = exercise.pause,
        initRPE = exercise.rpe.toString(),
        onBackClick = onBackClick,
        onSaveClick = { trainingId, title, sets, reps, pause, rpe ->
            onSaveClick(
                exercise.copy(
                    trainingUnitId = trainingId,
                    title = title,
                    sets = sets,
                    reps = reps,
                    pause = pause,
                    rpe = rpe
                )
            )
        }
    )
}
