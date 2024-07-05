package dev.didnt.proyecto.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import dev.didnt.proyecto.R
import dev.didnt.proyecto.entidad.Usuario
import dev.didnt.proyecto.servicio.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var lblUserName: TextView
    private lateinit var lblIdOnline: TextView
    private lateinit var lblUserEdad: TextView
    private lateinit var lblUserGenero: TextView
    private lateinit var lblUserEmail: TextView
    private lateinit var btnEdit: ImageButton

    private lateinit var editForm:LinearLayout
    private lateinit var txtModifyName: EditText
    private lateinit var txtModifyEmail: EditText
    private lateinit var btnGuardar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var userId = arguments?.getInt("id", 0)
        var userIdOnline = arguments?.getString("userIdOnline")
        var userPassword = arguments?.getString("userPassword")
        val userName = arguments?.getString("userName")
        var userEmail = arguments?.getString("userEmail")
        var userEdad = arguments?.getInt("userEdad", 0)
        var userGenero = arguments?.getString("userGenero")
        val gender:String
        if(userGenero=="M"){
            gender = "Masculino"
        }else{
            gender = "Femenino"
        }

        lblUserName = view.findViewById(R.id.lblNombreUser)
        lblIdOnline = view.findViewById(R.id.lblIdOnline)
        lblUserEdad = view.findViewById(R.id.lblEdadUser)
        lblUserGenero = view.findViewById(R.id.lblGeneroUser)
        lblUserEmail = view.findViewById(R.id.lblCorreoUser)

        lblUserName.text = userName
        lblIdOnline.text = "idOnline: "+userIdOnline
        lblUserEdad.text = "Edad: "+userEdad.toString()
        lblUserGenero.text = "Género: "+ gender
        lblUserEmail.text = userEmail

        editForm = view.findViewById(R.id.editForm)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        txtModifyName = view.findViewById(R.id.txtModifyName)
        txtModifyEmail = view.findViewById(R.id.txtModifyEmail)
        btnEdit = view.findViewById(R.id.btnEdit)

        btnEdit.setOnClickListener{
            editForm.visibility = View.VISIBLE

            txtModifyName.setText(userName)
            txtModifyEmail.setText(userEmail)
        }
        btnGuardar.setOnClickListener {
            editForm.visibility = View.GONE
            actualizar(userId)
        }

        return view
    }
    private fun actualizar(id:Int?){

        val nombre = txtModifyName.text.toString().trim()
        val email = txtModifyEmail.text.toString().trim()

        val user = Usuario(0, "", "", nombre, email, 0, "")

        CoroutineScope(Dispatchers.IO).launch {
            val rpta = id?.let { RetrofitClient.webService.modificarUsuario(it, user) }
                requireActivity().runOnUiThread {
                    if (rpta != null) {
                        if(rpta.isSuccessful){
                            Toast.makeText(context, rpta.body().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}