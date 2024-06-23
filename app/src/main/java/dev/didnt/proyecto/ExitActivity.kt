package dev.didnt.proyecto

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExitActivity : AppCompatActivity() {

    private lateinit var btnHome:ImageButton
    private lateinit var btnCerrar:Button
    private lateinit var lblUserName:TextView
    private lateinit var btnProfile: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    private fun asignarReferencias(){
        var userId = intent.getIntExtra("id", 0)
        println(userId)
        var userIdOnline = intent.getStringExtra("userIdOnline")
        var userPassword = intent.getStringExtra("userPassword")
        var userName = intent.getStringExtra("userName")
        var userEmail = intent.getStringExtra("userEmail")
        var userEdad = intent.getIntExtra("userEdad", 0)
        var userGenero = intent.getStringExtra("userGenero")

        btnCerrar = findViewById(R.id.btnCerrar)
        lblUserName = findViewById(R.id.lblUserName)
        btnHome = findViewById(R.id.btnHome)
        btnProfile = findViewById(R.id.btnProfile)

        lblUserName.text = userName + " :( ?"
        btnCerrar.setOnClickListener {
            val taskStackBuilder = TaskStackBuilder.create(this)
                .addNextIntent(Intent(this, IngresarActivity::class.java))
            taskStackBuilder.startActivities()
        }
        btnHome.setOnClickListener {
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
        btnProfile.setOnClickListener {
            finish()
            val intent = Intent(this, UserProfile::class.java)
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
}