package com.example.fitnesstrackandplan.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.ui.theme.ButtonBlue
import com.example.fitnesstrackandplan.ui.theme.ButtonGreen
import com.example.fitnesstrackandplan.ui.theme.TextCol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseForm(
    trainingId: Int?,
    formTitle: String = "Add exercise",
    initExTitle: String = "",
    initSets: String = "",
    initReps: String = "",
    initPause: String = "",
    initRPE: String = "",
    onSaveClick: (
        trainingId: Int,
        title: String,
        sets: Int,
        reps: Int,
        pause: String,
        rpe: Int
    ) -> Unit,
    onBackClick: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf(initExTitle) }
    var sets by rememberSaveable { mutableStateOf(initSets) }
    var reps by rememberSaveable { mutableStateOf(initReps) }
    var pause by rememberSaveable { mutableStateOf(initPause) }
    var rpe by rememberSaveable { mutableStateOf(initRPE) }


    val setsNumber = sets.toIntOrNull()
    val repsNumber = reps.toIntOrNull()
    val rpeNumber = rpe.toIntOrNull()

    val isInputValid = title.isNotBlank() &&
            setsNumber != null &&
            setsNumber > 0 &&
            repsNumber != null &&
            repsNumber > 0 &&
            pause.isNotBlank() &&
            rpeNumber != null &&
            rpeNumber > 0 &&
            rpeNumber < 11

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(formTitle) },
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
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Exercise title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sets,
                onValueChange = { sets = it },
                label = { Text("Sets") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Reps") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pause,
                onValueChange = { pause = it },
                label = { Text("Pause") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rpe,
                onValueChange = { rpe = it },
                label = { Text("RPE") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    contentColor = TextCol
                ),
                onClick = {
                    if (trainingId != null) {
                        onSaveClick(
                            trainingId,
                            title,
                            sets.toIntOrNull() ?: 0,
                            reps.toIntOrNull() ?: 0,
                            pause,
                            rpe.toIntOrNull() ?: 0
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isInputValid
            ) {
                Text("Save exercise")
            }
        }
    }
}