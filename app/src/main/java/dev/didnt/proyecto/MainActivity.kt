package dev.didnt.proyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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
    private lateinit var btnProfile: ImageButton
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

    private fun asignarRefencias(){
        var userId = intent.getStringExtra("idOnline")
        var userPassword = intent.getStringExtra("userPassword")
        var userName = intent.getStringExtra("nombre")
        var userEmail = intent.getStringExtra("email")
        var userEdad = intent.getIntExtra("edad", 0)
        var userGenero = intent.getStringExtra("genero")

        rvNoticias = findViewById(R.id.rvNews)
        rvNoticias.layoutManager = LinearLayoutManager(this)
        rvCategorias = findViewById(R.id.rvCategories)
        rvCategorias.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        btnProfile = findViewById(R.id.btnProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("userPassword", userPassword)
            intent.putExtra("userName", userName)
            intent.putExtra("userEmail", userEmail)
            intent.putExtra("userEdad", userEdad)
            intent.putExtra("userGenero", userGenero)
            startActivity(intent)
        }
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
}