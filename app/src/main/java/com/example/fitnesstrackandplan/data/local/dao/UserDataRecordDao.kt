package com.example.fitnesstrackandplan.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fitnesstrackandplan.model.UserDataRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataRecordDao {

    @Insert
    suspend fun insertRecord(record: UserDataRecord)

    @Query("SELECT * FROM user_data_records WHERE fieldName = :fieldName ORDER BY date DESC")
    fun getRecordsByField(fieldName: String): Flow<List<UserDataRecord>>
}