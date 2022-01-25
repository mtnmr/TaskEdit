package com.example.taskedit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView) {
    var todoText: TextView? = null
    var statusText: TextView? = null

    init {
        todoText = itemView.findViewById(R.id.todoText)
        statusText = itemView.findViewById(R.id.statusText)
    }
}
