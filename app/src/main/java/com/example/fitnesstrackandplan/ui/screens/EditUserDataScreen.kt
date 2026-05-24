package com.example.fitnesstrackandplan.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserDataScreen(
    fieldName: String,
    currentValue: String,
    onBackClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf(currentValue) }

    val keyboardType = when(fieldName) {
        "age", "height" -> KeyboardType.Number
        "weight" -> KeyboardType.Decimal
        else -> KeyboardType.Text
    }

    val isValid = when(fieldName) {
        "age", "height" -> value.toIntOrNull() != null
        "weight" -> value.toDoubleOrNull() != null
        else -> value.isNotBlank()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit $fieldName") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text(fieldName) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                )
            )

            Button(
                onClick = {
                    onSaveClick(value)
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}