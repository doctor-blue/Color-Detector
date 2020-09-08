package com.doctorblue.colordetector.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.doctorblue.colordetector.R
import com.doctorblue.colordetector.model.UserColor
import kotlinx.android.synthetic.main.dialog_color_detail.*

class ColorDetailDialog(
    context: Context,
    private val color: UserColor,
    private val onRemove: (UserColor) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_color_detail)
        setTitle(context.resources.getString(R.string.your_color))

        view_color_preview.setBackgroundColor(Color.parseColor(color.hex))

        txt_rgb.text = ("RGB(${color.r}, ${color.g}, ${color.b})")
        txt_hex.text = ("Hex : ${color.hex}")
        txt_hsl.text = ("HSL(${color.h}, ${color.s}, ${color.l})")


        btn_cancel.setOnClickListener { dismiss() }

        btn_remove_color.setOnClickListener {
            onRemove(color)
            dismiss()
        }

    }
}