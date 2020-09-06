package com.doctorblue.colordetector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.colordetector.R
import com.doctorblue.colordetector.model.UserColor
import com.google.android.material.card.MaterialCardView

class ColorAdapter(private val context: Context) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    private var colors: List<UserColor> = listOf()

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.card_color_item)
        fun onBind(color: UserColor) {
            cardView.setCardBackgroundColor(android.graphics.Color.parseColor(color.hex))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.color_item,parent,false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.onBind(colors[position])
    }

    override fun getItemCount(): Int = colors.size

    fun notifyData(colors: List<UserColor>) {
        this.colors = colors
        notifyDataSetChanged()
    }
}