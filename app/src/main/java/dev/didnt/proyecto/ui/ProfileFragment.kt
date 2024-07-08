package dev.didnt.proyecto.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.didnt.proyecto.R
import dev.didnt.proyecto.entidad.Usuario

class ProfileFragment : Fragment() {

    private lateinit var profileActivity:LinearLayout
    private lateinit var lblUserName: TextView
    private lateinit var lblIdOnline: TextView
    private lateinit var lblUserEdad: TextView
    private lateinit var lblUserGenero: TextView
    private lateinit var lblUserEmail: TextView
    private lateinit var lblFechaNacimiento: TextView
    private lateinit var btnEdit: ImageButton

    private lateinit var editForm:LinearLayout
    private lateinit var txt_edit_name: EditText
    private lateinit var txt_edit_idOnline: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnCancelarEdit: Button
    private lateinit var profileInfo: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val fbUser = FirebaseAuth.getInstance().currentUser
        val usersRef = FirebaseDatabase.getInstance().getReference("Usuarios")
        val currentUser = usersRef.child(fbUser!!.uid)

        profileActivity = view.findViewById(R.id.profileActivity)
        lblUserName = view.findViewById(R.id.lblNombreUser)
        lblIdOnline = view.findViewById(R.id.lblIdOnline)
        lblUserEdad = view.findViewById(R.id.lblEdadUser)
        lblUserGenero = view.findViewById(R.id.lblGeneroUser)
        lblUserEmail = view.findViewById(R.id.lblCorreoUser)
        lblFechaNacimiento = view.findViewById(R.id.lblFechaUser)

        currentUser.get().addOnSuccessListener {dataSnapshot->
            if(dataSnapshot.exists()){
                val userData = dataSnapshot.getValue() as Map<*,*>

                val nombre = userData["nombre"] as String
                val idOnline = userData["idOnline"] as String
                val genero = userData["genero"] as String
                val fechaNacimiento = userData["fechaNacimiento"] as String
                val email = userData["email"] as String
                val edad = userData["edad"] as Any

                //EditForm
                txt_edit_idOnline.setText(idOnline)
                txt_edit_name.setText(nombre)

                //UserInfo
                lblUserName.text = nombre
                lblIdOnline.text = "idOnline: "+idOnline
                lblUserEdad.text = "Edad: "+edad.toString()
                lblUserGenero.text = "Género: "+ genero
                lblUserEmail.text = email
                lblFechaNacimiento.text = "Fecha de nacimiento: "+fechaNacimiento
            }
        }
        editForm = view.findViewById(R.id.editForm)
        profileInfo = view.findViewById(R.id.profile_info)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnCancelarEdit = view.findViewById(R.id.btnCancelarEdit)
        txt_edit_idOnline = view.findViewById(R.id.txt_edit_idOnline)
        txt_edit_name = view.findViewById(R.id.txt_edit_name)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener{
            val edit_bg: Drawable? = context?.getDrawable(R.drawable.edit_form_bg)
            profileActivity.background = edit_bg
            profileInfo.visibility = View.GONE
            editForm.visibility = View.VISIBLE
        }
        btnCancelarEdit.setOnClickListener {
            val normal_bg: Drawable? = context?.getDrawable(R.drawable.profile_bg)
            profileActivity.background = normal_bg
            profileInfo.visibility = View.VISIBLE
            editForm.visibility = View.GONE
        }

        btnGuardar.setOnClickListener {
            val normal_bg: Drawable? = context?.getDrawable(R.drawable.profile_bg)
            profileActivity.background = normal_bg
            profileInfo.visibility = View.VISIBLE
            editForm.visibility = View.GONE

            val updatedData = mapOf(
                "idOnline" to txt_edit_idOnline.text.toString().trim(),
                "nombre" to txt_edit_name.text.toString().trim()
            )

            currentUser.updateChildren(updatedData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {e->
                Toast.makeText(requireContext(), "Error al actualizar los datos \n ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        currentUser.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userData = snapshot.getValue(Usuario::class.java)
                    if (userData != null) {
                        actualizarUI(userData)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })

        return view
    }
    private fun actualizarUI(usuario: Usuario){
        lblIdOnline.text = usuario.idOnline
        lblUserName.text = usuario.nombre
    }
}