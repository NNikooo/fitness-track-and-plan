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
import com.example.fitnesstrackandplan.ui.forms.ExerciseForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    trainingId: Int?,
    onBackClick: () -> Unit,
    onSaveClick: (
        trainingId: Int,
        title: String,
        sets: Int,
        reps: Int,
        pause: String,
        rpe: Int
    ) -> Unit
) {
    ExerciseForm(
        trainingId = trainingId,
        onBackClick = onBackClick,
        onSaveClick = onSaveClick
    )


}