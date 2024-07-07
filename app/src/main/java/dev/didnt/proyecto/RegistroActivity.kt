package dev.didnt.proyecto

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.didnt.proyecto.entidad.VideoLoop
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
    private lateinit var background: VideoView
    private lateinit var auth:FirebaseAuth
    private lateinit var reference:DatabaseReference
    private lateinit var btnCancelar:Button

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
        auth = Firebase.auth
        background = findViewById(R.id.videoView)
        registroNombre = findViewById(R.id.registroNombre)
        registroUser = findViewById(R.id.registroUser)
        registroCorreo = findViewById(R.id.registroCorreo)
        registroContra = findViewById(R.id.registroContra)
        registroFecha= findViewById(R.id.registroFecha)
        registroUser = findViewById(R.id.registroUser)
        btnCancelar = findViewById(R.id.btnCancelar)

        btnM= findViewById(R.id.btnM)
        btnF= findViewById(R.id.btnF)

        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            validarCampos()
        }
        registroFecha.setOnClickListener{
            showDatePicker()
        }
        btnCancelar.setOnClickListener{
            finish()
        }
        background.setVideoPath("android.resource://"+packageName+"/"+R.raw.app_bg)
        background.setOnPreparedListener(VideoLoop())
        background.start()
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

    private fun validarCampos() {
        val nombre = registroNombre.text.toString()
        val idOnline = registroUser.text.toString()
        val correo = registroCorreo.text.toString()
        val password = registroContra.text.toString()
        val fechaNacimiento = registroFecha.text.toString()
        val edad =calcularEdad(fechaNacimiento)
        val genero = if (btnM.isChecked) "Masculino" else if (btnF.isChecked) "Femenino" else "No Especificado"

        if(nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || fechaNacimiento.isEmpty() || idOnline.isEmpty()){
            Toast.makeText(this, "Complete el formulario", Toast.LENGTH_SHORT).show()
        }else{
            registrar(idOnline.trim(), password.trim(), nombre.trim(), correo.trim(), edad, genero, fechaNacimiento)
        }
    }

    private fun registrar(idOnline:String, userPassword:String, nombre:String, email:String, edad:Int, genero:String, fechaNacimiento: String){
        auth.createUserWithEmailAndPassword(email, userPassword)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val uid:String = auth.currentUser!!.uid
                    reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                    val hashmap = HashMap<String, Any>()

                    hashmap["uid"] = uid
                    hashmap["idOnline"] = idOnline
                    hashmap["nombre"] = nombre
                    hashmap["email"] = email
                    hashmap["edad"] = edad
                    hashmap["genero"] = genero
                    hashmap["fechaNacimiento"] = fechaNacimiento

                    reference.updateChildren(hashmap).addOnCompleteListener {task2->
                        if(task2.isSuccessful){
                            Toast.makeText(this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show()
                            finish()
                            val intent = Intent(this, IngresarActivity::class.java)
                            startActivity(intent)
                        }
                    }.addOnFailureListener {e->
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
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
    override fun onPause() {
        super.onPause()
        background.pause()
    }

    override fun onResume() {
        super.onResume()
        background.start()
    }
}

