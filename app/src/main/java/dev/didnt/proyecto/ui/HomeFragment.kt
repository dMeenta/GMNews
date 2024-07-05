package dev.didnt.proyecto.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.R
import dev.didnt.proyecto.adaptadores.RvAdapterNoticias
import dev.didnt.proyecto.entidad.Noticia
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var rvNoticias: RecyclerView
    private var adaptadorNoticias: RvAdapterNoticias = RvAdapterNoticias()
    private var listaNoticia:ArrayList<Noticia> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvNoticias = view.findViewById(R.id.rvNews)
        rvNoticias.layoutManager = LinearLayoutManager(rvNoticias.context)

        cargarDatos()

        return view
    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitClient.webService.obtenerNoticias()
            requireActivity().runOnUiThread {
                if(rpta.isSuccessful){
                    listaNoticia = rpta.body()!!.listaNoticias
                    adaptadorNoticias.agregarDatosNoticias(listaNoticia)
                    mostrarDatos();
                }
            }
        }
    }
    private fun mostrarDatos(){
        rvNoticias.adapter = adaptadorNoticias
    }
}