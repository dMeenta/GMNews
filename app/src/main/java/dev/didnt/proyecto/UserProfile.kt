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
    private lateinit var btnHome: ImageButton
    private lateinit var btnGuardar: Button
    private lateinit var txtModifyName: EditText
    private lateinit var txtModifyEmail: EditText
    private lateinit var txtModifyEdad: EditText
    private lateinit var btnEdit: ImageButton
    private lateinit var lblEditar: TextView
    private lateinit var lblUserEmail:TextView
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
        btnHome = findViewById(R.id.btnHome)
        lblUserName = findViewById(R.id.lblNombreUser)
        lblIdOnline = findViewById(R.id.lblIdOnline)
        lblUserEdad = findViewById(R.id.lblEdadUser)
        lblUserGenero = findViewById(R.id.lblGeneroUser)
        lblUserEmail = findViewById(R.id.lblCorreoUser)

        lblUserName.setText(intent.getStringExtra("userName"))
        lblIdOnline.setText("idOnline: "+intent.getStringExtra("userIdOnline"))
        lblUserEdad.setText("Edad: "+intent.getIntExtra("userEdad", 0).toString())
        lblUserGenero.setText("Genero: "+intent.getStringExtra("userGenero"))
        lblUserEmail.setText(intent.getStringExtra("userEmail"))


        lblEditar = findViewById(R.id.lblEditar)
        btnGuardar = findViewById(R.id.btnGuardar)
        txtModifyName = findViewById(R.id.txtModifyName)
        txtModifyEmail = findViewById(R.id.txtModifyEmail)
        txtModifyEdad = findViewById(R.id.txtModifyEdad)
        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener{
            txtModifyName.visibility = View.VISIBLE
            txtModifyEmail.visibility = View.VISIBLE
            txtModifyEdad.visibility = View.VISIBLE
            btnGuardar.visibility = View.VISIBLE
            lblEditar.visibility = View.VISIBLE
        }
        btnGuardar.setOnClickListener {
            txtModifyName.visibility = View.INVISIBLE
            txtModifyEmail.visibility = View.INVISIBLE
            txtModifyEdad.visibility = View.INVISIBLE
            btnGuardar.visibility = View.INVISIBLE
            lblEditar.visibility = View.INVISIBLE
        }
        btnHome.setOnClickListener{
            finish()
        }
    }
}