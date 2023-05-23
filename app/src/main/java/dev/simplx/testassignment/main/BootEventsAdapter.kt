package dev.simplx.testassignment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.simplx.domain.bootevent.BootEvent
import dev.simplx.testassignment.R
import java.text.SimpleDateFormat

class BootEventsAdapter(
    private val data: List<BootEvent>,
) : RecyclerView.Adapter<BootEventsAdapter.BootItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BootItemViewHolder {
        val formNameView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_boot, parent, false)
        return BootItemViewHolder(formNameView)
    }

    override fun onBindViewHolder(holder: BootItemViewHolder, position: Int) {
        holder.numberTextView.text = position.toString()
        holder.timestampTextView.text = data[position].bootTime.toString()
    }

    class BootItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var numberTextView: TextView
        var timestampTextView: TextView

        init {
            numberTextView = itemView.findViewById(R.id.number)
            timestampTextView = itemView.findViewById(R.id.timestamp)
        }
    }

    override fun getItemCount(): Int = data.size

}