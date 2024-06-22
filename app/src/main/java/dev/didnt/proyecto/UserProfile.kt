package dev.didnt.proyecto

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserProfile : AppCompatActivity() {

    private lateinit var lblUserName:TextView
    private lateinit var lblIdOnline:TextView
    private lateinit var lblUserEdad:TextView
    private lateinit var lblUserGenero:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias(){
        lblUserName = findViewById(R.id.lblNombreUser)
        lblIdOnline = findViewById(R.id.lblIdOnline)
        lblUserEdad = findViewById(R.id.lblEdadUser)
        lblUserGenero = findViewById(R.id.lblGeneroUser)

        lblUserName.setText(intent.getStringExtra("userName"))
        lblIdOnline.setText("idOnline: "+intent.getStringExtra("userId"))
        lblUserEdad.setText("Edad: "+intent.getIntExtra("userEdad", 0).toString())
        lblUserGenero.setText("Genero: "+intent.getStringExtra("userGenero"))
    }
}