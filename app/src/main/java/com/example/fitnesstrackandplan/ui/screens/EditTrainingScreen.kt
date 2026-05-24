package com.example.fitnesstrackandplan.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.forms.TrainingForm
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingScreen(
    trainingUnit: TrainingUnit?,
    trainingTypes: List<String>,
    onBackClick: () -> Unit,
    onSaveClick: (TrainingUnit) -> Unit
) {
    if (trainingUnit == null) {
        Text("Training not found")
        return
    }

    TrainingForm(
        formTitle = "Edit training",
        initTrgTitle = trainingUnit.title,
        initType = trainingUnit.type,
        initTime = trainingUnit.time.toString(),
        initDate = trainingUnit.date.toString(),
        initDuration = trainingUnit.duration.toString(),
        trainingTypes = trainingTypes,
        onSaveClick = { title, type, date, time, duration ->
            onSaveClick(
                trainingUnit.copy(
                    title = title,
                    type = type,
                    date = LocalDate.parse(date),
                    time = LocalTime.parse(time),
                    duration = duration
                )
            )
        },
        onBackClick = onBackClick
    )
}
