package dev.didnt.proyecto

import android.content.Intent
import android.content.SyncRequest
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class IngresarActivity : AppCompatActivity() {

    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var btnLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar)

        txtUsername = findViewById(R.id.txtLoginUsername)
        txtPassword = findViewById(R.id.txtLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val idOnline = txtUsername.text.toString().trim()
            val contraseña = txtPassword.text.toString().trim()

            if (idOnline.isNotEmpty() && contraseña.isNotEmpty()) {
                val usuario = Usuario(0, "", "", contraseña, idOnline, 0, "")
                loginUsuario(usuario)
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUsuario(usuario: Usuario) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.webService.login(usuario)

                if (response.isSuccessful) {
                    val usuarioResponse = response.body()
                    if (usuarioResponse != null) {
                        // Login exitoso, puedes guardar información de sesión si es necesario
                        startActivity(Intent(this@IngresarActivity, MainActivity::class.java))
                        finish()
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@IngresarActivity, "Respuesta del servidor vacía", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@IngresarActivity, "Error en el inicio de sesión: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@IngresarActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}