package com.doctorblue.colordetector.utils

import android.content.Context
import android.widget.LinearLayout
import com.doctorblue.colordetector.R
import com.google.android.material.card.MaterialCardView

class ColorCardView(context: Context) : MaterialCardView(context) {

    private var size = context.resources.getDimensionPixelSize(R.dimen._15sdp)
    private var cardRadius = size / 2f
    private var cardLayoutParams = LinearLayout.LayoutParams(size, size)


    init {
        cardLayoutParams.marginStart = context.resources.getDimensionPixelSize(R.dimen._5sdp)
        this.layoutParams = cardLayoutParams
        this.radius = cardRadius
    }

    fun setCardSize(size: Int, marginStart: Int = 0) {
        this.size = size
        cardRadius = size / 2f

        this.layoutParams.height = size
        this.layoutParams.width = size
        (this.layoutParams as LinearLayout.LayoutParams).marginStart = marginStart

        this.radius = cardRadius

    }
}