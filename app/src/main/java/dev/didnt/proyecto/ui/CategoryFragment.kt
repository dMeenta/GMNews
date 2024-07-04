package dev.didnt.proyecto.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.R
import dev.didnt.proyecto.adaptadores.RvAdapterCategorias
import dev.didnt.proyecto.entidad.Juego
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryFragment : Fragment() {
    private lateinit var rvCategorias: RecyclerView
    private var adaptadorCategorias:RvAdapterCategorias = RvAdapterCategorias()
    private var listaJuegos:ArrayList<Juego> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        rvCategorias = view.findViewById(R.id.rvCategorias)
        rvCategorias.layoutManager = GridLayoutManager(rvCategorias.context, 2)

        cargarDatos()

        return view
    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitClient.webService.obtenerJuegos()
            requireActivity().runOnUiThread {
                if(rpta.isSuccessful){
                    listaJuegos = rpta.body()!!.listaJuegos
                    adaptadorCategorias.agregarDatosCategorias(listaJuegos)
                    mostrarDatos();
                }
            }
        }
    }

    private fun mostrarDatos(){
        rvCategorias.adapter = adaptadorCategorias
    }
}