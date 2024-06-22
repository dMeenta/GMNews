package dev.didnt.proyecto

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.entidad.Juego
import dev.didnt.proyecto.entidad.Noticia
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var rvNoticias: RecyclerView
    private lateinit var rvCategorias: RecyclerView
    private var adaptadorNoticias:AdaptadorPersonalizadoNoticias = AdaptadorPersonalizadoNoticias()
    private var adaptadorCategorias:AdaptadorPersonalizadoJuegos = AdaptadorPersonalizadoJuegos()
    private var listaNoticia:ArrayList<Noticia> = ArrayList()
    private var listaJuegos:ArrayList<Juego> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarRefencias()
        cargarDatos();
    }
    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitClient.webService.obtenerNoticias()
            val rpta2 = RetrofitClient.webService.obtenerJuegos()
            runOnUiThread {
                if(rpta.isSuccessful){
                    listaNoticia = rpta.body()!!.listaNoticias
                    listaJuegos = rpta2.body()!!.listaJuegos
                    adaptadorNoticias.agregarDatosNoticias(listaNoticia)
                    adaptadorCategorias.agregarDatosCategorias(listaJuegos)
                    mostrarDatos();
                }else{
                    Log.d("===", "Error en servicio")
                }
            }
        }
    }

    private fun mostrarDatos(){
        rvNoticias.adapter = adaptadorNoticias
        rvCategorias.adapter = adaptadorCategorias
    }

    private fun asignarRefencias(){
        rvNoticias = findViewById(R.id.rvNews)
        rvNoticias.layoutManager = LinearLayoutManager(this)
        rvCategorias = findViewById(R.id.rvCategories)
        rvCategorias.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}