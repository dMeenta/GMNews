package dev.didnt.proyecto

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExitActivity : AppCompatActivity() {

    private lateinit var btnCerrar:Button
    private lateinit var lblUserName:TextView

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
        btnCerrar = findViewById(R.id.btnCerrar)
        lblUserName = findViewById(R.id.lblUserName)

        lblUserName.text = intent.getStringExtra("userName")
        btnCerrar.setOnClickListener {
            finish()
            val taskStackBuilder = TaskStackBuilder.create(this)
                .addNextIntent(Intent(this, IngresarActivity::class.java))
            taskStackBuilder.startActivities()
        }
    }
}