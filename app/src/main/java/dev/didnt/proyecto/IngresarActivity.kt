package dev.didnt.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.didnt.proyecto.entidad.Usuario
import dev.didnt.proyecto.entidad.VideoLoop
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngresarActivity : AppCompatActivity() {
    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnRegister:Button
    private lateinit var background:VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar)

        background = findViewById(R.id.videoView)
        background.setVideoPath("android.resource://"+packageName+"/"+R.raw.app_bg)
        background.setOnPreparedListener(VideoLoop())
        background.start()

        txtUsername = findViewById(R.id.txtLoginUsername)
        txtPassword = findViewById(R.id.txtLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val intent =Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val idOnline = txtUsername.text.toString().trim()
            val userPassword = txtPassword.text.toString().trim()

            if (idOnline.isNotEmpty() && userPassword.isNotEmpty()) {
                val user = Usuario(0,idOnline, userPassword, "", "", 0, "")
                CoroutineScope(Dispatchers.IO).launch {
                    val res = RetrofitClient.webService.login(user)
                    runOnUiThread {
                        if(res.isSuccessful){
                            val usuario = res.body()
                            if(usuario != null){
                                val intent = Intent(this@IngresarActivity, MainActivity::class.java)
                                intent.putExtra("id", usuario.id)
                                intent.putExtra("userIdOnline", usuario.idOnline)
                                intent.putExtra("userPassword", usuario.userPassword)
                                intent.putExtra("userName", usuario.nombre)
                                intent.putExtra("userEmail", usuario.email)
                                intent.putExtra("userEdad", usuario.edad)
                                intent.putExtra("userGenero", usuario.genero)
                                finish()
                                startActivity(intent)
                            }
                        }
                    }
                }
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
    override fun onPause() {
        super.onPause()
        background.pause()
    }

    override fun onResume() {
        super.onResume()
        background.start()
    }
}