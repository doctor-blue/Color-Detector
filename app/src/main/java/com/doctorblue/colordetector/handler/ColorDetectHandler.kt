package com.doctorblue.colordetector.handler

import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import androidx.camera.view.PreviewView
import kotlin.math.max
import kotlin.math.min

class ColorDetectHandler {
    private var red = 0
    private var green = 0
    private var blue = 0

    private var rgb = 0
    private var hex: String = ""

    private var name: String = ""

    private var h: String = ""
    private var s: String = ""
    private var l: String = ""

    private var bitmap: Bitmap? = null

    private fun reset() {
        red = 0
        green = 0
        blue = 0
        rgb = 0
        hex = ""
        name = ""
    }


    fun detect(cameraView: PreviewView, pointer: View): com.doctorblue.colordetector.model.Color {

        // Reset all variables before to detect new color
        reset()

        // Get the coordinate of pointer
        // Add half of width and half of height to x and y
        // to be able to target the center of the pointer
        val x = pointer.x + (pointer.width / 2).toFloat()
        val y = pointer.y + (pointer.height / 2).toFloat()

        // Create a bitmap using camera view
        // # Note: It is important that bitmap size should
        // # be equals to or be greater than pointer size
        bitmap = cameraView.bitmap

        // Get the pixel of pointer using its x and y coordinate
        val pixel = bitmap!!.getPixel(x.toInt(), y.toInt())

        // Get RGB - r (red), g (green) and b (blue) - colors of the pixel
        red = Color.red(pixel)
        green = Color.green(pixel)
        blue = Color.blue(pixel)

        // This is the color which can be used to set color of something directly
        // # Example: set an image view's color
        // # ImageView.setBackgroundColor(rgb)
        rgb = Color.rgb(red, green, blue)

        // Get hex code of RGB
        hex = "#" + Integer.toHexString(rgb and 0x00ffffff)


        return com.doctorblue.colordetector.model.Color(
            hex,
            red.toString(),
            green.toString(),
            blue.toString(),
        )
    }

    fun detect(
        bitmap: Bitmap,
        pointer: View,
        marginTop: Float,
        marginLeft: Float,
        ratio: Float
    ): com.doctorblue.colordetector.model.Color {

        // Reset data
        reset()

        // Get the coordinate of pointer
        // Add half of width and half of height to x and y
        // to be able to target the center of the pointer
        val x = (pointer.x + (pointer.width / 2).toFloat() - marginLeft) * ratio
        val y = (pointer.y + (pointer.height / 2).toFloat() - marginTop) * ratio
        // Get the pixel of pointer using its x and y coordinate


        val pixel = bitmap.getPixel(x.toInt(), y.toInt())

        // Get RGB - r (red), g (green) and b (blue) - colors of the pixel
        red = Color.red(pixel)
        green = Color.green(pixel)
        blue = Color.blue(pixel)

        // This is the color which can be used to set color of something directly
        // # Example: set an image view's color
        // # ImageView.setBackgroundColor(rgb)
        rgb = Color.rgb(red, green, blue)

        // Get hex code of RGB
        hex = "#" + Integer.toHexString(rgb and 0x00ffffff)

        return com.doctorblue.colordetector.model.Color(
            hex,
            red.toString(),
            green.toString(),
            blue.toString(),
        )
    }

    private fun convertRgbToHsl(red: Int, green: Int, blue: Int): IntArray {
        val rgb = doubleArrayOf(
            red.toFloat() / 255.toDouble(),
            green.toFloat() / 255.toDouble(),
            blue.toFloat() / 255
                .toDouble()
        )
        val r = rgb[0]
        val g = rgb[1]
        val b = rgb[2]
        val min: Double
        val max: Double
        val delta: Double
        var s: Double
        val l: Double
        min = min(r, min(g, b))
        max = max(r, max(g, b))
        delta = max - min
        l = (min + max) / 2
        s = 0.0
        if (l > 0 && l < 1) s = delta / if (l < 0.5) 2 * l else 2 - 2 * l
        var h = 0.0
        if (delta > 0) {
            if (max == r && max != g) h += (g - b) / delta
            if (max == g && max != b) h += 2 + (b - r) / delta
            if (max == b && max != r) h += 4 + (r - g) / delta
            h /= 6.0
        }
        val factor = 255.0
        return intArrayOf(
            (h * factor).toInt(),
            (s * factor).toInt(),
            (l * factor).toInt()
        )
    }
}