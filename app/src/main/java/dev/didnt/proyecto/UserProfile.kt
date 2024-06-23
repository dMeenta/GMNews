package dev.didnt.proyecto

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserProfile : AppCompatActivity() {
    private lateinit var btnGuardar: Button
    private lateinit var txtModifyName: EditText
    private lateinit var txtModifyEmail: EditText
    private lateinit var txtModifyEdad: EditText
    private lateinit var btnEdit: ImageButton
    private var modificar:Boolean = false
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

        btnGuardar = findViewById(R.id.btnGuardar)
        txtModifyName = findViewById(R.id.txtModifyName)
        txtModifyEmail = findViewById(R.id.txtModifyEmail)
        txtModifyEdad = findViewById(R.id.txtModifyEdad)
        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener{
            modificar = true
        }
        if(modificar){
            txtModifyName.visibility = View.VISIBLE
            txtModifyEmail.visibility = View.VISIBLE
            txtModifyEdad.visibility = View.VISIBLE
            btnGuardar.visibility = View.VISIBLE
        }
    }
}