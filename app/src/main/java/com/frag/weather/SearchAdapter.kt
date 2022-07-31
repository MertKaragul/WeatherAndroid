package com.frag.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.frag.weather.Model.SearchModel

class SearchAdapter(val searchesModel : SearchModel) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(){
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_weather , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.weather_search_text_view).text = searchesModel.name.toString()
    }

    override fun getItemCount(): Int {
        return 1
    }
}