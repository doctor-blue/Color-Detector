package com.doctorblue.colordetector.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doctorblue.colordetector.model.UserColor

@Database(entities = [UserColor::class], version = 1)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun getColorDao(): ColorDao

    companion object {
        @Volatile
        private var instance: ColorDatabase? = null
        fun getInstance(context: Context): ColorDatabase {
            if (instance == null)
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ColorDatabase::class.java,
                        "ColorDatabase.db"
                    ).build()
                }
            return instance!!
        }
    }
}