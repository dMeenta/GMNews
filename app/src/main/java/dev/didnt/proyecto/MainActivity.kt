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
    private lateinit var btnExit: ImageButton
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
        var userId = intent.getIntExtra("id", 0)
        println(userId)
        var userIdOnline = intent.getStringExtra("userIdOnline")
        var userPassword = intent.getStringExtra("userPassword")
        var userName = intent.getStringExtra("userName")
        var userEmail = intent.getStringExtra("userEmail")
        var userEdad = intent.getIntExtra("userEdad", 0)
        var userGenero = intent.getStringExtra("userGenero")


        rvNoticias = findViewById(R.id.rvNews)
        rvNoticias.layoutManager = LinearLayoutManager(this)
        rvCategorias = findViewById(R.id.rvCategories)
        rvCategorias.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        btnProfile = findViewById(R.id.btnProfile)
        btnProfile.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("userIdOnline", userIdOnline)
            intent.putExtra("userPassword", userPassword)
            intent.putExtra("userName", userName)
            intent.putExtra("userEmail", userEmail)
            intent.putExtra("userEdad", userEdad)
            intent.putExtra("userGenero", userGenero)
            startActivity(intent)
        }
        btnExit = findViewById(R.id.btnExit)
        btnExit.setOnClickListener {
            val intent = Intent(this, ExitActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("userIdOnline", userIdOnline)
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