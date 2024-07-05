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
        val userName = arguments?.getString("userName")

        btnExit = view.findViewById(R.id.btnCerrar)
        lblUserName = view.findViewById(R.id.lblUserName)

        lblUserName.text = userName + " :( ?"
        btnExit.setOnClickListener {
            val activity = requireActivity()
            val taskStackBuilder = TaskStackBuilder.create(activity)
                .addNextIntent(Intent(activity, IngresarActivity::class.java))
            taskStackBuilder.startActivities()
        }
        return view
    }
}