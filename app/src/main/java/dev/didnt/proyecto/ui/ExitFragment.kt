package dev.didnt.proyecto.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.TaskStackBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dev.didnt.proyecto.IngresarActivity
import dev.didnt.proyecto.R

class ExitFragment : Fragment() {
    private lateinit var btnExit: Button
    private lateinit var lblUserName: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exit, container, false)

        val fbUser = FirebaseAuth.getInstance().currentUser
        val usersRef = FirebaseDatabase.getInstance().getReference("Usuarios")
        val currentUser = usersRef.child(fbUser!!.uid)

        currentUser.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val userData = dataSnapshot.getValue() as Map<*, *>

                val nombre = userData["nombre"] as String

                lblUserName.text = nombre + " :( ?"
            }
        }

        btnExit = view.findViewById(R.id.btnCerrar)
        lblUserName = view.findViewById(R.id.lblUserName)


        btnExit.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val activity = requireActivity()
            val taskStackBuilder = TaskStackBuilder.create(activity)
                .addNextIntent(Intent(activity, IngresarActivity::class.java))
            taskStackBuilder.startActivities()
        }
        return view
    }
}