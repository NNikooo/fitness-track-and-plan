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
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import android.app.TimePickerDialog
import com.example.fitnesstrackandplan.ui.cards.TrainingCard
import com.example.fitnesstrackandplan.ui.forms.TrainingForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrainingScreen(
    trainingTypes: List<String>,
    onBackClick: () -> Unit,
    onSaveClick: (
        title: String,
        type: String,
        date: String,
        time: String,
        duration: Int
    ) -> Unit
) {
    TrainingForm(
        trainingTypes = trainingTypes,
        onBackClick = onBackClick,
        onSaveClick = onSaveClick
    )
}