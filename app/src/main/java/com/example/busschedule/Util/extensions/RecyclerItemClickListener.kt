package com.example.busschedule.Util.extensions

import androidx.recyclerview.widget.RecyclerView


fun <T : RecyclerView.ViewHolder> T.onClick(event: ( position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke( getAdapterPosition(), getItemViewType())
    }
    return this
}
fun <T : RecyclerView.ViewHolder> T.onLongClick(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnLongClickListener {
        event.invoke( getAdapterPosition(), getItemViewType())
        return@setOnLongClickListener true
    }
    return this
}