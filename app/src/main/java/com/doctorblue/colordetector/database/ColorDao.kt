package com.doctorblue.colordetector.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doctorblue.colordetector.model.UserColor

@Dao
interface ColorDao {
    @Insert
    suspend fun insertColor(color: UserColor)

    @Insert
    suspend fun insertAllColor(colors: List<UserColor>)

    @Delete
    suspend fun deleteColor(color: UserColor)

    @Query("select * from UserColor")
    fun getAllColor(): LiveData<List<UserColor>>


}