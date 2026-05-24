package com.example.fitnesstrackandplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.fitnesstrackandplan.navigation.AppNavigation
import com.example.fitnesstrackandplan.ui.screens.IntroScreen
import com.example.fitnesstrackandplan.ui.screens.ProfileSetupScreen
import com.example.fitnesstrackandplan.ui.theme.FitnessTrackAndPlanTheme
import com.example.fitnesstrackandplan.worker.TrainingReminderWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        requestNotificationPermission()
        scheduleTrainingReminder()
        val sharedPreferences =
            getSharedPreferences("app_preferences", MODE_PRIVATE)
        val introFinishedFromPrefs =
            sharedPreferences.getBoolean("intro_finished", false)
        setContent {
            var introFinished by rememberSaveable {
                mutableStateOf(introFinishedFromPrefs)
            }
            FitnessTrackAndPlanTheme {
                if (!introFinished) {
                    IntroScreen(
                        onFinishClick = {
                            sharedPreferences.edit()
                                .putBoolean("intro_finished", true)
                                .apply()
                            introFinished = true
                        }
                    )
                } else {
                    AppNavigation()
                }
            }
        }
    }
    private fun scheduleTrainingReminder() {
        val workRequest = PeriodicWorkRequestBuilder<TrainingReminderWorker>(
            1,
            TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "training_reminder",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }
    }
}