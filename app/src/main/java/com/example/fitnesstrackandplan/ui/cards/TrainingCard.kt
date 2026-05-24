package com.example.fitnesstrackandplan.ui.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.theme.CardBlue
import com.example.fitnesstrackandplan.ui.theme.CardGreen

@Composable
fun TrainingCard(
    trainingUnit: TrainingUnit,
    onClick: () -> Unit
) {
    val cardColor = if (trainingUnit.completed) {
        CardGreen
    } else {
        CardBlue
    }
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = trainingUnit.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Type: ${trainingUnit.type}")
            Text(text = "Date: ${trainingUnit.date}")
            Text(text = "Duration: ${trainingUnit.duration} min")
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (trainingUnit.completed) "Status: finished" else "Status: planned"
            )
        }
    }
}