package com.talitamorales.servicegoo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.adapters.ServiceAdapter
import com.talitamorales.servicegoo.models.Service

class SearchFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ServiceAdapter
    private lateinit var searchView: SearchView

    private fun listServices() = arrayListOf(
        Service("Haircut", "$50.00"),
        Service("Manicure", "$35.00"),
        Service("Massage", "$80.00"),
        Service("Hair Remove", "$40.00"),
        Service("Facial Aesthetics", "$100.00"),
        Service("Hairstyling", "$120.00"),
        Service("Barbershop", "$45.00"),
        Service("Nail Extension", "$90.00"),
        Service("Skin Cleansing", "$75.00"),
        Service("Makeup", "$150.00")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerViewSearch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val listServices = listServices()

        adapter = ServiceAdapter(listServices) { service ->
            /*
            TODO: - Adicionar evento do clique ao clicar em um item da lista
            TODO: O que fazer? Ir para outra tela?
             Esconder a lista de servicos e mostrar o mapa com os locais que possuem o serviçø?
             */
        }

        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText?.lowercase() ?: ""
                val filteredList= listServices.filter {
                    it.name.lowercase().contains(text)
                }
                adapter.updateList(filteredList)
                return true
            }
        })
        return view
    }
}