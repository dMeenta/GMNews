package dev.didnt.proyecto

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class GameActivity : AppCompatActivity() {

    private lateinit var btnSalirJuego:ImageButton
    private lateinit var lblJuegoNombre:TextView
    private lateinit var imgJuego:ImageView
    private lateinit var lblDescription:TextView
    private lateinit var lblFecha:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()

        btnSalirJuego.setOnClickListener {
            finish()
        }
    }

    private fun asignarReferencias(){
        btnSalirJuego = findViewById(R.id.btnSalirJuego)
        imgJuego = findViewById(R.id.imgViewJuego)
        lblJuegoNombre = findViewById(R.id.lblJuegoNombre)
        lblDescription = findViewById(R.id.lblDescription)
        lblFecha = findViewById(R.id.lblFecha)

        val juegoNombre = intent.getStringExtra("nombre")
        val juegoDesc = intent.getStringExtra("descripcion")
        val juegoImg = intent.getStringExtra("imgUrl")
        val juegoFecha = intent.getStringExtra("fecha_lanzamiento")

        lblJuegoNombre.text = juegoNombre
        lblDescription.text = juegoDesc
        lblFecha.text = "Fecha de Lanzamiento: "+ juegoFecha

        Glide.with(this).load(juegoImg).into(imgJuego)
    }
}