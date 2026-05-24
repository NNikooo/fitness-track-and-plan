package com.example.fitnesstrackandplan.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitnesstrackandplan.model.TrainingUnit
import com.example.fitnesstrackandplan.ui.screens.AddExerciseScreen
import com.example.fitnesstrackandplan.ui.screens.AddTrainingScreen
import com.example.fitnesstrackandplan.ui.screens.EditExerciseScreen
import com.example.fitnesstrackandplan.ui.screens.EditTrainingScreen
import com.example.fitnesstrackandplan.ui.screens.EditUserDataScreen
import com.example.fitnesstrackandplan.ui.screens.HomeScreen
import com.example.fitnesstrackandplan.ui.screens.ProfileSetupScreen
import com.example.fitnesstrackandplan.ui.screens.StatsScreen
import com.example.fitnesstrackandplan.ui.screens.TrainingDetailScreen
import com.example.fitnesstrackandplan.ui.screens.UserDataHistoryScreen
import com.example.fitnesstrackandplan.ui.screens.UserProfileScreen
import com.example.fitnesstrackandplan.viewmodel.TrainingViewModel
import java.time.LocalDate

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val trainingViewModel: TrainingViewModel = viewModel()

    val userProfile = trainingViewModel.userProfile

    if (userProfile == null) {
        ProfileSetupScreen(
            onSaveClick = { profile ->
                trainingViewModel.saveUserProfile(profile)
            }
        )
        return
    }

    val bottomRoutes = listOf("history", "today", "future", "stats", "profile")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val trainingTypes = trainingViewModel.trainings
        .map { it.type }
        .distinct()

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "history",
                        onClick = {
                            navController.navigate("history") {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.ArrowBack, contentDescription = "History") },
                        label = { Text("History") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "today",
                        onClick = {
                            navController.navigate("today") {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Today") },
                        label = { Text("Today") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "future",
                        onClick = {
                            navController.navigate("future") {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.ArrowForward, contentDescription = "Future") },
                        label = { Text("Future") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "stats",
                        onClick = {
                            navController.navigate("stats") {
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.List, contentDescription = "Stats") },
                        label = { Text("Stats") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "profile",
                        onClick = {
                            navController.navigate("profile") {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "today",
            modifier = Modifier.padding(paddingValues)
        ) {


            composable("today") {
                val today = LocalDate.now()
                val todayTrainings = trainingViewModel.trainings
                    .filter { it.date == today }
                    .sortedBy { it.time }
                HomeScreen(
                    trainings = todayTrainings,
                    onAddClick = {
                        navController.navigate("add_training")
                    },
                    onTrainingClick = { trainingId ->
                        navController.navigate("training_detail/$trainingId")
                    }
                )
            }
            composable("history") {
                val today = LocalDate.now()
                val historyTrainings = trainingViewModel.trainings
                    .filter { it.date.isBefore(today) }
                    .sortedWith(compareByDescending<TrainingUnit> { it.date }.thenBy { it.time })
                HomeScreen(
                    trainings = historyTrainings,
                    onAddClick = {
                        navController.navigate("add_training")
                    },
                    onTrainingClick = { trainingId ->
                        navController.navigate("training_detail/$trainingId")

                    }
                )
            }
            composable("future") {
                val today = LocalDate.now()
                val futureTrainings = trainingViewModel.trainings
                    .filter { it.date.isAfter(today) }
                    .sortedWith(compareBy<TrainingUnit> { it.date }.thenBy { it.time })
                HomeScreen(
                    trainings = futureTrainings,
                    onAddClick = {
                        navController.navigate("add_training")
                    },
                    onTrainingClick = { trainingId ->
                        navController.navigate("training_detail/$trainingId")

                    }
                )
            }
            composable("stats") {
                StatsScreen(trainingViewModel.trainings)
            }
            composable("profile") {
                UserProfileScreen(
                    userProfile = trainingViewModel.userProfile!!,
                    onShowHistoryClick = { fieldName ->
                        navController.navigate("user_data_history/$fieldName")
                    },
                    onEditClick = { fieldName ->
                        navController.navigate("edit_user_data/$fieldName")
                    }
                )
            }
            composable("add_training") {
                AddTrainingScreen(
                    trainingTypes = trainingTypes,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = { title, type, date, time, duration ->
                        trainingViewModel.addTraining(title, type, date, time, duration)
                    }
                )
            }
            composable("training_detail/{trainingId}") { backStackEntry ->
                val trainingId = backStackEntry.arguments
                    ?.getString("trainingId")
                    ?.toIntOrNull()
                val trainingUnit = trainingId?.let {
                    trainingViewModel.getTrainingById(it)
                }
                if (trainingId != null) {
                    trainingViewModel.loadExercisesForTraining(trainingId)
                }
                TrainingDetailScreen(
                    trainingUnit = trainingUnit,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onFinishClick = { id ->
                        trainingViewModel.markAsFinished(id)
                        navController.popBackStack()
                    },
                    onEditClick = { id ->
                        navController.navigate("edit_training/$id")
                    },
                    onDeleteClick = { trainingUnit ->
                        trainingViewModel.deleteTraining(trainingUnit)
                    },
                    onAddExerciseClick = { id ->
                        navController.navigate("add_exercise/$id")
                    },
                    exercises = trainingViewModel.exercises,
                    onDeleteExerciseClick = { exercise ->
                        trainingViewModel.deleteExercise(exercise)
                    },
                    onEditExerciseClick = { exerciseId ->
                        navController.navigate("edit_exercise/$exerciseId")
                    }
                )
            }
            composable("add_exercise/{trainingId}") { backStackEntry ->
                val trainingId = backStackEntry.arguments
                    ?.getString("trainingId")
                    ?.toIntOrNull()

                AddExerciseScreen(
                    trainingId = trainingId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = { id, title, sets, reps, pause, rpe ->
                        trainingViewModel.addExercise(
                            trainingUnitId = id,
                            title = title,
                            sets = sets,
                            reps = reps,
                            pause = pause,
                            rpe = rpe
                        )
                        navController.popBackStack()
                    }
                )
            }
            composable("edit_training/{trainingId}") { backStackEntry ->
                val trainingId = backStackEntry.arguments
                    ?.getString("trainingId")
                    ?.toIntOrNull()
                val trainingUnit = trainingId?.let {
                    trainingViewModel.getTrainingById(it)
                }
                EditTrainingScreen(
                    trainingUnit = trainingUnit,
                    trainingTypes = trainingTypes,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = { updatedTraining ->
                        trainingViewModel.updateTraining(updatedTraining)
                        navController.popBackStack()
                    }
                )
            }
            composable("edit_exercise/{exerciseId}") { backStackEntry ->
                val exerciseId = backStackEntry.arguments
                    ?.getString("exerciseId")
                    ?.toIntOrNull()
                val exercise = exerciseId?.let {
                    trainingViewModel.getExerciseById(it)
                }

                EditExerciseScreen(
                    exercise = exercise,
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = { updateExercise ->
                        trainingViewModel.updateExercise(updateExercise)
                        navController.popBackStack()
                    }
                )
            }
            composable("user_data_history/{fieldName}") { backStackEntry ->

                val fieldName = backStackEntry.arguments
                    ?.getString("fieldName")
                    ?: ""

                LaunchedEffect(fieldName) {
                    trainingViewModel.loadUserDataRecords(fieldName)
                }

                UserDataHistoryScreen(
                    fieldName = fieldName,
                    records = trainingViewModel.userDataRecords,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable("edit_user_data/{fieldName}") { backStackEntry ->

                val fieldName = backStackEntry.arguments
                    ?.getString("fieldName")
                    ?: ""

                val profile = trainingViewModel.userProfile!!

                val currentValue = when (fieldName) {
                    "name" -> profile.name
                    "age" -> profile.age.toString()
                    "height" -> profile.height.toString()
                    "weight" -> profile.weight.toString()
                    else -> ""
                }

                EditUserDataScreen(
                    fieldName = fieldName,
                    currentValue = currentValue,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSaveClick = { newValue ->
                        trainingViewModel.updateUserProfileField(fieldName, newValue)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
