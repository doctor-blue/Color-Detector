package com.doctorblue.colordetector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doctorblue.colordetector.R
import com.doctorblue.colordetector.model.UserColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class ColorListAdapter(private val context: Context, private val onItemClick: (UserColor) -> Unit) :
    RecyclerView.Adapter<ColorListAdapter.ColorListViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var colors: List<UserColor> = listOf()
    private var names: List<String> = listOf()

    inner class ColorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView = itemView.findViewById(R.id.txt_name_of_list)
        private val rvColor: RecyclerView = itemView.findViewById(R.id.rv_color)

        fun onBind(name: String) {

            txtTitle.text = name

            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = ColorAdapter(context, ColorAdapter.Geometry.SQUARE, onItemClick)
            rvColor.layoutManager = layoutManager
            rvColor.setHasFixedSize(true)
            rvColor.adapter = adapter
            rvColor.setRecycledViewPool(viewPool)

            CoroutineScope(Dispatchers.Main).launch {
                val color = colors.asFlow().filter { it.name == name }.toList()
                adapter.notifyData(color)

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.color_list_item, parent, false)
        return ColorListViewHolder(view)

    }

    override fun onBindViewHolder(holder: ColorListViewHolder, position: Int) {
        holder.onBind(names[position])
    }

    override fun getItemCount(): Int = names.size

    fun notifyData(names: List<String>, colors: List<UserColor>) {
        this.names = names
        this.colors = colors
        notifyDataSetChanged()
    }
}