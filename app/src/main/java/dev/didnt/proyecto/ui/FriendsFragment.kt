package dev.didnt.proyecto.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.didnt.proyecto.R
import dev.didnt.proyecto.adaptadores.UserAdapter
import dev.didnt.proyecto.entidad.Usuario

class FriendsFragment : Fragment() {

    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userList:ArrayList<Usuario>
    private lateinit var adapter: UserAdapter
    private lateinit var dbReference:DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        auth = FirebaseAuth.getInstance()

        dbReference = FirebaseDatabase.getInstance().getReference().child("Usuarios")
        dbReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(Usuario::class.java)
                    if(auth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        userList = ArrayList()
        adapter = UserAdapter(requireContext(), userList)
        userRecyclerView = view.findViewById(R.id.rvUsuarios)

        userRecyclerView.layoutManager = LinearLayoutManager(userRecyclerView.context)
        userRecyclerView.adapter = adapter

        return view
    }
}