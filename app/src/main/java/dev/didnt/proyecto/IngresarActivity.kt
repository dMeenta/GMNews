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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dev.didnt.proyecto.entidad.VideoLoop

class IngresarActivity : AppCompatActivity() {
    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnRegister:Button
    private lateinit var background:VideoView
    private lateinit var auth:FirebaseAuth
    var fbUser:FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar)
        auth = Firebase.auth

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
            val idOnline = txtUsername.text.toString()
            val userPassword = txtPassword.text.toString()

            if(idOnline.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show()
            }else{
                iniciarSesion(idOnline, userPassword)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun iniciarSesion(idOnline: String, userPassword: String) {
        auth.signInWithEmailAndPassword(idOnline, userPassword)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    finish()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e->
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkSesion(){
        fbUser = FirebaseAuth.getInstance().currentUser
        if(fbUser!=null){
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        checkSesion()
        super.onStart()
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