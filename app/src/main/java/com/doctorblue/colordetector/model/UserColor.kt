package com.doctorblue.colordetector.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserColor(
    var hex: String = "#000000",
    var r: String = "",
    var g: String = "",
    var b: String = "",
    var h: String = "",
    var s: String = "",
    var l: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String = ""

    constructor() : this("")
}