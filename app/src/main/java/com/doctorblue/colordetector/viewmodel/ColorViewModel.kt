package com.doctorblue.colordetector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctorblue.colordetector.model.Color

class ColorViewModel : ViewModel() {

    private val _color: MutableLiveData<Color> = MutableLiveData()

    val color: LiveData<Color> = _color

    fun setColor(color: Color) {
        _color.value = color
    }

}