package com.talitamorales.servicegoo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.models.Service

class ServiceAdapter(
    private var listServices: ArrayList<Service>,
    private val onItemClick: (Service) -> Unit
): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>(){

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameService: TextView = view.findViewById(R.id.nameService)
        val priceService: TextView = view.findViewById(R.id.priceService)
    }

    private var listFilter: List<Service> = listServices.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return  ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = listFilter[position]
        holder.nameService.text = service.name
        holder.priceService.text = service.priceService
        holder.itemView.setOnClickListener {
            onItemClick(service)
        }
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Service>) {
        listFilter = newList
        notifyDataSetChanged()
    }
}