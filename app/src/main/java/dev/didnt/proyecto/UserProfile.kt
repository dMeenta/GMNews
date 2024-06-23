package dev.didnt.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.didnt.proyecto.entidad.Usuario
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfile : AppCompatActivity() {
    private lateinit var btnExit: ImageButton
    private lateinit var btnHome: ImageButton
    private lateinit var btnGuardar: Button
    private lateinit var txtModifyName: EditText
    private lateinit var txtModifyEmail: EditText
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
        btnExit = findViewById(R.id.btnExit)

        var userId = intent.getIntExtra("id", 0)
        println(userId)
        var userIdOnline = intent.getStringExtra("userIdOnline")
        var userPassword = intent.getStringExtra("userPassword")
        var userName = intent.getStringExtra("userName")
        var userEmail = intent.getStringExtra("userEmail")
        var userEdad = intent.getIntExtra("userEdad", 0)
        var userGenero = intent.getStringExtra("userGenero")
        if(userGenero=="M"){
            userGenero = "Masculino"
        }else{
            userGenero = "Femenino"
        }

        lblUserName.text = userName
        lblIdOnline.text = "idOnline: "+userIdOnline
        lblUserEdad.text = "Edad: "+userEdad.toString()
        lblUserGenero.text = "Genero: "+ userGenero
        lblUserEmail.text = userEmail

        lblEditar = findViewById(R.id.lblEditar)
        btnGuardar = findViewById(R.id.btnGuardar)
        txtModifyName = findViewById(R.id.txtModifyName)
        txtModifyEmail = findViewById(R.id.txtModifyEmail)
        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener{
            txtModifyName.visibility = View.VISIBLE
            txtModifyEmail.visibility = View.VISIBLE
            btnGuardar.visibility = View.VISIBLE
            lblEditar.visibility = View.VISIBLE

            txtModifyName.setText(userName)
            txtModifyEmail.setText(userEmail)

        }
        btnGuardar.setOnClickListener {
            txtModifyName.visibility = View.INVISIBLE
            txtModifyEmail.visibility = View.INVISIBLE
            btnGuardar.visibility = View.INVISIBLE
            lblEditar.visibility = View.INVISIBLE
            actualizar()
        }
        btnHome.setOnClickListener{
            finish()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("userIdOnline", userIdOnline)
            intent.putExtra("userPassword", userPassword)
            intent.putExtra("userName", userName)
            intent.putExtra("userEmail", userEmail)
            intent.putExtra("userEdad", userEdad)
            intent.putExtra("userGenero", userGenero)
            startActivity(intent)
        }
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
    private fun actualizar(){
        val id = intent.getIntExtra("id", 0)
        val nombre = txtModifyName.text.toString().trim()
        val email = txtModifyEmail.text.toString().trim()

        val user = Usuario(0, "", "", nombre, email, 0, "")

        CoroutineScope(Dispatchers.IO).launch {
            val res = RetrofitClient.webService.modificarUsuario(id, user)
            runOnUiThread {
                if(res.isSuccessful){
                    Toast.makeText(baseContext, res.body().toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}