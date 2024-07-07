package dev.didnt.proyecto.adaptadores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.ChatActivity
import dev.didnt.proyecto.R
import dev.didnt.proyecto.entidad.Usuario

class UserAdapter(val context: Context, val userList: ArrayList<Usuario>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        val lblNombre = view.findViewById<TextView>(R.id.lblNombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):UserViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_component, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userItem = userList[position]
        holder.lblNombre.text = userItem.nombre
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("nombre", userItem.nombre)
            intent.putExtra("uid", userItem.uid)

            context.startActivity(intent)
        }
    }

}