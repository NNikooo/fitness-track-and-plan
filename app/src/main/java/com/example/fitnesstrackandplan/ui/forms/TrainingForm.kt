package com.example.fitnesstrackandplan.ui.forms

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.ui.theme.ButtonBlue
import com.example.fitnesstrackandplan.ui.theme.ButtonGreen
import com.example.fitnesstrackandplan.ui.theme.TextCol
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingForm(
    formTitle: String = "Add training",
    initTrgTitle: String = "",
    initType: String = "",
    initDate: String = "",
    initTime: String = "",
    initDuration: String = "",
    trainingTypes: List<String> = emptyList(),
    onSaveClick: (
        title: String,
                  type: String,
                  date: String,
                  time: String,
                  duration: Int
            ) -> Unit,
    onBackClick: () -> Unit

) {

    var title by rememberSaveable { mutableStateOf(initTrgTitle) }
    var type by rememberSaveable { mutableStateOf(initType) }
    val allTypes = (trainingTypes)
        .distinct()
        .sorted()

    var expanded by rememberSaveable { mutableStateOf(false) }
    var customType by rememberSaveable { mutableStateOf("") }

    var date by rememberSaveable { mutableStateOf(initDate) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = String.format(
                "%04d-%02d-%02d",
                year,
                month + 1,
                dayOfMonth
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    var time by rememberSaveable { mutableStateOf(initTime) }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            time = String.format(
                "%02d:%02d",
                hour,
                minute
            )
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    var duration by rememberSaveable { mutableStateOf(initDuration) }

    val durationNumber = duration.toIntOrNull()
    val isFormValid = title.isNotBlank() &&
            type.isNotBlank() &&
            date.isNotBlank() &&
            time.isNotBlank() &&
            durationNumber != null &&
            durationNumber > 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(formTitle) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
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
                label = { Text("Training title") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = type,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    trainingTypes.forEach { selectedType ->
                        DropdownMenuItem(
                            text = { Text(selectedType) },
                            onClick = {
                                type = selectedType
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = customType,
                onValueChange = { customType = it },
                label = { Text("Custom type") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = TextCol
                ),
                onClick = {
                    if (customType.isNotBlank()) {
                        type = customType
                        customType = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Use custom type")
            }


            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text("Date") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = buttonColors(
                containerColor = ButtonBlue,
                    contentColor = TextCol
            ),
                onClick = {
                    datePickerDialog.show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select date")
            }

            OutlinedTextField(
                value = time,
                onValueChange = {},
                label = { Text("Time") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = TextCol
                ),
                onClick = {
                    timePickerDialog.show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select time")
            }

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration in minutes") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                colors = buttonColors(
                    containerColor = ButtonGreen,
                    contentColor = TextCol
                ),
                onClick = {
                    val durationNumber = duration.toIntOrNull() ?: 0
                    onSaveClick(
                        title,
                        type,
                        date,
                        time,
                        durationNumber
                    )
                    onBackClick()
                },
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save training")
            }
        }
    }
}