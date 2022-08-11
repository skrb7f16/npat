package com.skrb7f16.npat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skrb7f16.npat.R


class NamesOfPlayerAdapter(private val myList:MutableList<String>): RecyclerView.Adapter<NamesOfPlayerAdapter.NamesViewHolder>() {
    class NamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.nameOfPlayerSingle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.name_item_single,parent,false)
        return NamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamesViewHolder, position: Int) {
        val item=myList[position]
        holder.name.text = item
    }

    override fun getItemCount(): Int {
        println(myList.size)
        println(myList.size)
        println(myList.size)
        return myList.size
    }

}