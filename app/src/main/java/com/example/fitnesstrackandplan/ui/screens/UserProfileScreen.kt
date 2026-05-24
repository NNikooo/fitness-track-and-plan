package com.example.fitnesstrackandplan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.UserProfile
import com.example.fitnesstrackandplan.ui.theme.ButtonBlue
import com.example.fitnesstrackandplan.ui.theme.ButtonGreen
import com.example.fitnesstrackandplan.ui.theme.TextCol

@Composable
fun UserProfileScreen(
    userProfile: UserProfile,
    onShowHistoryClick: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("User profile", style = MaterialTheme.typography.headlineMedium)

        UserDataCard("Name", userProfile.name, fieldName = "name", onShowHistoryClick = onShowHistoryClick, onEditClick = onEditClick)
        UserDataCard("Age", userProfile.age.toString(), fieldName = "age", onShowHistoryClick = onShowHistoryClick, onEditClick = onEditClick)
        UserDataCard("Height", userProfile.height.toString(), " cm", fieldName = "height", onShowHistoryClick = onShowHistoryClick, onEditClick = onEditClick)
        UserDataCard("Weight", userProfile.weight.toString(), " kg", fieldName = "weight", onShowHistoryClick = onShowHistoryClick, onEditClick = onEditClick)
    }
}

@Composable
fun UserDataCard(
    title: String,
    data: String,
    unit: String = "",
    fieldName: String,
    onShowHistoryClick: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("$title: $data$unit")

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBlue,
                        contentColor = TextCol
                    ),
                    onClick = {
                        onEditClick(fieldName)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonGreen,
                        contentColor = TextCol
                    ),
                    onClick = {
                        onShowHistoryClick(fieldName)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("History")
                }
            }
        }
    }
}