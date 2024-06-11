package dev.didnt.proyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.entidad.Noticia
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var rvNoticias: RecyclerView
    private var adaptador:AdaptadorPersonalizado = AdaptadorPersonalizado()
    private var listaNoticia:ArrayList<Noticia> = ArrayList()
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
            runOnUiThread {
                if(rpta.isSuccessful){
                    listaNoticia = rpta.body()!!.listaNoticias
                    adaptador.agregarDatos(listaNoticia)
                    mostrarDatos();
                }else{
                    Log.d("===", "Error en servicio")
                }
            }
        }
    }

    private fun mostrarDatos(){
        rvNoticias.adapter = adaptador
    }

    private fun asignarRefencias(){
        rvNoticias = findViewById(R.id.rvNews)
        rvNoticias.layoutManager = LinearLayoutManager(this)
    }
}