package dev.didnt.proyecto

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.didnt.proyecto.entidad.Usuario
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var registroNombre:EditText
    private lateinit var registroUser:EditText
    private lateinit var registroCorreo:EditText
    private lateinit var registroContra:EditText
    private lateinit var registroFecha: EditText
    private lateinit var btnRegistrar:Button

    private lateinit var btnF:RadioButton
    private lateinit var btnM:RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        asigarnarReferencias()
    }

    fun asigarnarReferencias(){
        registroNombre = findViewById(R.id.registroNombre)
        registroUser = findViewById(R.id.registroUser)
        registroCorreo = findViewById(R.id.registroCorreo)
        registroContra = findViewById(R.id.registroContra)
        registroFecha= findViewById(R.id.registroFecha)
        registroUser = findViewById(R.id.registroUser)

        btnM= findViewById(R.id.btnM)
        btnF= findViewById(R.id.btnF)


        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            if (validarCampos()) {
                agregar()
            } else {
                mostrarMensaje("Debe completar los campos obligatorios.")
            }
        }

    }

    private fun validarCampos(): Boolean {
        val nombre = registroNombre.text.toString().trim()
        val correo = registroCorreo.text.toString().trim()
        val contraseña = registroContra.text.toString().trim()
        val fechaNacimiento = registroFecha.text.toString().trim()

        return nombre.isNotEmpty() && correo.isNotEmpty() && contraseña.isNotEmpty() && fechaNacimiento.isNotEmpty()
    }

    private fun agregar(){
        val  nombre = registroNombre.text.toString()
        val  email = registroCorreo.text.toString()
        val  contraseña = registroContra.text.toString()
        val id_online =registroUser.text.toString()
        val fechaNacimiento = registroFecha.text.toString()
        val edad =calcularEdad(fechaNacimiento)
        val genero = if (btnM.isChecked) "M" else if (btnF.isChecked) "F" else ""
        val usuario = Usuario(1,nombre,email,contraseña,id_online,edad,genero)

        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitClient.webService.registrarUsuario(usuario)
            runOnUiThread {
                if(rpta.isSuccessful){
                    mostrarMensaje(rpta.body().toString())
                }
            }
        }

    }


    private fun calcularEdad(fechaNacimiento: String): Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val nacimiento = sdf.parse(fechaNacimiento)
        val fechaNCal = Calendar.getInstance().apply { time = nacimiento }
        val hoy = Calendar.getInstance()

        var edad = hoy.get(Calendar.YEAR) - fechaNCal.get(Calendar.YEAR)
        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNCal.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }


    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar",DialogInterface.OnClickListener{dialog, which ->
            val intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

}

