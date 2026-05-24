package com.example.fitnesstrackandplan.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesstrackandplan.data.local.TrainingDatabase
import com.example.fitnesstrackandplan.model.TrainingUnit
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.fitnesstrackandplan.model.TrainingExercise
import androidx.compose.runtime.mutableStateOf
import com.example.fitnesstrackandplan.model.UserProfile
import java.time.LocalDate
import java.time.LocalTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.fitnesstrackandplan.model.UserDataRecord

class TrainingViewModel(application: Application)
    : AndroidViewModel(application) {

    // dao interface a metody
    private val trainingDao =
        TrainingDatabase
            .getDatabase(application)
            .trainingUnitDao()

    private val exerciseDao =
        TrainingDatabase
            .getDatabase(application)
            .trainingExerciseDao()

    private val profileDao =
        TrainingDatabase
            .getDatabase(application)
            .userProfileDao()

    private val userDataRecordDao =
        TrainingDatabase.getDatabase(application).userDataRecordDao()


    // listy uchovavajuce data z databazy
    private val _trainings = mutableStateListOf<TrainingUnit>()
    private val _exercises = mutableStateListOf<TrainingExercise>()
    private val _userDataRecords = mutableStateListOf<UserDataRecord>()
    private var _userProfile by mutableStateOf<UserProfile?>(null)

    // enkapsulacia
    val trainings: List<TrainingUnit> = _trainings
    val exercises: List<TrainingExercise> = _exercises
    val userDataRecords: List<UserDataRecord> = _userDataRecords
    val userProfile: UserProfile?
        get() = _userProfile

    // pri inicializacci viewmodel nacita treningy a profil
    init {
        loadTrainings()
        loadUserProfile()
    }

    // 2 sukromne metody na nacitanie treningov a profilov
    private fun loadTrainings() {
        viewModelScope.launch {
            trainingDao.getAllTrainings().collectLatest {
                _trainings.clear()
                _trainings.addAll(it)
            }
        }
    }
    private fun loadUserProfile() {
        viewModelScope.launch {
            _userProfile = profileDao.getProfile()
        }
    }

    // dalsie metody na pracu s databazou
    fun addTraining(
        title: String,
        type: String,
        date: String,
        time: String,
        duration: Int
    ) {
        viewModelScope.launch {
            trainingDao.insertTraining(
                TrainingUnit(
                    title = title,
                    type = type,
                    date = LocalDate.parse(date),
                    time = LocalTime.parse(time),
                    duration = duration,
                    completed = false
                )
            )
        }
    }

    fun getTrainingById(id: Int): TrainingUnit? {
        return trainings.find { it.id == id }
    }

    fun markAsFinished(id: Int) {
        val training =
            trainings.find { it.id == id }

        if (training != null) {
            viewModelScope.launch {
                trainingDao.updateTraining(
                    training.copy(
                        completed = true
                    )
                )
            }
        }
    }

    fun deleteTraining(trainingUnit: TrainingUnit) {
        viewModelScope.launch {
            trainingDao.deleteTraining(trainingUnit)
        }
    }

    fun updateTraining(trainingUnit: TrainingUnit) {
        viewModelScope.launch {
            trainingDao.updateTraining(trainingUnit)
        }
    }

    fun loadExercisesForTraining(trainingUnitId: Int) {
        viewModelScope.launch {
            exerciseDao.getExercisesForTraining(trainingUnitId).collectLatest {
                _exercises.clear()
                _exercises.addAll(it)
            }
        }
    }

    fun getExerciseById(id: Int): TrainingExercise? {
        return exercises.find { it.id == id }
    }
    fun addExercise(
        trainingUnitId: Int,
        title: String,
        sets: Int,
        reps: Int,
        pause: String,
        rpe: Int
    ) {
        viewModelScope.launch {
            exerciseDao.insertExercise(
                TrainingExercise(
                    trainingUnitId = trainingUnitId,
                    title = title,
                    sets = sets,
                    reps = reps,
                    pause = pause,
                    rpe = rpe
                )
            )
        }
    }

    fun updateExercise(exercise: TrainingExercise) {
        viewModelScope.launch {
            exerciseDao.updateExercise(exercise)
        }
    }

    fun deleteExercise(exercise: TrainingExercise) {
        viewModelScope.launch {
            exerciseDao.deleteExercise(exercise)
        }
    }

    fun addUserDataRecord(fieldName: String, value: String) {
        viewModelScope.launch {
            userDataRecordDao.insertRecord(
                UserDataRecord(
                    fieldName = fieldName,
                    value = value,
                    date = LocalDate.now()
                )
            )
        }
    }

    fun loadUserDataRecords(fieldName: String) {
        viewModelScope.launch {
            userDataRecordDao.getRecordsByField(fieldName).collectLatest {
                _userDataRecords.clear()
                _userDataRecords.addAll(it)
            }
        }
    }

    fun updateUserProfileField(
        fieldName: String,
        newValue: String
    ) {
        val currentProfile = userProfile ?: return

        val updatedProfile = when (fieldName) {
            "name" -> currentProfile.copy(name = newValue)
            "age" -> currentProfile.copy(age = newValue.toIntOrNull() ?: currentProfile.age)
            "height" -> currentProfile.copy(height = newValue.toIntOrNull() ?: currentProfile.height)
            "weight" -> currentProfile.copy(weight = newValue.toDoubleOrNull() ?: currentProfile.weight)
            else -> currentProfile
        }
        viewModelScope.launch {
            profileDao.insertProfile(updatedProfile)
            userDataRecordDao.insertRecord(
                UserDataRecord(
                    fieldName = fieldName,
                    value = newValue,
                    date = LocalDate.now()
                )
            )
            _userProfile = updatedProfile
        }
    }


    fun saveUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            profileDao.insertProfile(profile)

            userDataRecordDao.insertRecord(
                UserDataRecord(fieldName = "name", value = profile.name, date = LocalDate.now())
            )
            userDataRecordDao.insertRecord(
                UserDataRecord(fieldName = "age", value = profile.age.toString(), date = LocalDate.now())
            )
            userDataRecordDao.insertRecord(
                UserDataRecord(fieldName = "height", value = profile.height.toString(), date = LocalDate.now())
            )
            userDataRecordDao.insertRecord(
                UserDataRecord(fieldName = "weight", value = profile.weight.toString(), date = LocalDate.now())
            )

            _userProfile = profile
        }
    }
}