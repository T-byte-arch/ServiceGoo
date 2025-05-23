package com.talitamorales.servicegoo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.models.LocalService

class ServiceAdapter(
    private var listServices: ArrayList<LocalService>,
    private val onItemClick: (LocalService) -> Unit
): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>(){

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameService: TextView = view.findViewById(R.id.nameService)
        val priceService: TextView = view.findViewById(R.id.priceService)
    }

    private var listFilter: List<LocalService> = listServices.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return  ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = listFilter[position]
        holder.nameService.text = service.name
        holder.priceService.text = service.price
        holder.itemView.setOnClickListener {
            onItemClick(service)
        }
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<LocalService>) {
        listFilter = newList
        notifyDataSetChanged()
    }
}