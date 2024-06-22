package dev.didnt.proyecto

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
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
    private val calendar = Calendar.getInstance()

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
        registroFecha.setOnClickListener{
            showDatePicker()
        }
    }

    private fun showDatePicker() {

        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->

                val selectedDate = Calendar.getInstance()

                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val formattedDate = dateFormat.format(selectedDate.time)

                registroFecha.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun validarCampos():Boolean {
        val nombre = registroNombre.text
        val idOnline = registroUser.text
        val correo = registroCorreo.text
        val password = registroContra.text
        val fechaNacimiento = registroFecha.text

        return nombre.isNotEmpty() && correo.isNotEmpty() && password.isNotEmpty() && fechaNacimiento.isNotEmpty() && idOnline.isNotEmpty()
    }

    private fun agregar(){
        val idOnline =registroUser.text.toString()
        val password = registroContra.text.toString()
        val nombre = registroNombre.text.toString()
        val email = registroCorreo.text.toString()
        val fechaNacimiento = registroFecha.text.toString()
        val edad =calcularEdad(fechaNacimiento)
        val genero = if (btnM.isChecked) "M" else if (btnF.isChecked) "F" else ""

        val usuario = Usuario(idOnline,password,nombre,email,edad,genero)

        CoroutineScope(Dispatchers.IO).launch {
            val rpta = RetrofitClient.webService.registrarUsuario(usuario)
            runOnUiThread {
                if(rpta.isSuccessful){
                    mostrarMensajeS(rpta.body().toString())
                }
            }
        }

    }


    private fun calcularEdad(fechaNacimiento: String): Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val nacimiento = sdf.parse(fechaNacimiento)
        val fechaNCal = Calendar.getInstance().apply { time = nacimiento }
        val hoy = Calendar.getInstance()

        var edad = hoy.get(Calendar.YEAR) - fechaNCal.get(Calendar.YEAR)
        if (hoy.get(Calendar.DAY_OF_YEAR) < fechaNCal.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }


    private fun mostrarMensajeS(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener{dialog, which ->
                val intent =Intent(this,IngresarActivity::class.java)
                startActivity(intent)
            })
        ventana.create().show()
    }

    private fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Información")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", null)
        ventana.create().show()
    }

}

