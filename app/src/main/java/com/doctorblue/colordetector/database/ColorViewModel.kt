package com.doctorblue.colordetector.database

import android.app.Application
import androidx.lifecycle.*
import com.doctorblue.colordetector.model.UserColor
import kotlinx.coroutines.launch

class ColorViewModel(application: Application) : AndroidViewModel(application) {

    private val colorRepository = ColorRepository(application)
    fun insertColor(color: UserColor) = viewModelScope.launch {
        colorRepository.insertColor(color)
    }

    fun deleteColor(color: UserColor) = viewModelScope.launch {
        colorRepository.deleteColor(color)
    }

    fun getAllColor(): LiveData<List<UserColor>> {
        return colorRepository.getAllColor()
    }


    class ColorViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColorViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }

    }

}