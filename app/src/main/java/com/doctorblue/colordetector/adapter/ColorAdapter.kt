package com.doctorblue.colordetector.adapter

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.colordetector.R
import com.doctorblue.colordetector.model.UserColor
import com.doctorblue.colordetector.utils.ColorCardView

class ColorAdapter(
    private val context: Context,
    private val geometry: Geometry = Geometry.CIRCLE,
    private val onItemClick: (UserColor) -> Unit = {}
) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    var colors: List<UserColor> = listOf()

    class ColorViewHolder(private val cardView: ColorCardView) :
        RecyclerView.ViewHolder(cardView) {
        fun onBind(color: UserColor, onItemClick: (UserColor) -> Unit) {

            cardView.setCardBackgroundColor(Color.parseColor(color.hex))

            cardView.setOnClickListener { onItemClick(color) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val cardView = ColorCardView(context)

        if (geometry == Geometry.SQUARE) {
            val size = context.resources.getDimensionPixelSize(R.dimen._24sdp)
            cardView.setCardSize(size)
            cardView.radius = 0f
        }

        return ColorViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.onBind(colors[position], onItemClick)
    }

    override fun getItemCount(): Int = colors.size

    fun notifyData(colors: List<UserColor>) {
        this.colors = colors
        notifyDataSetChanged()
    }

    enum class Geometry {
        CIRCLE, SQUARE
    }
}