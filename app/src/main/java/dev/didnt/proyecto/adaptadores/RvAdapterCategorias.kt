package dev.didnt.proyecto.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.didnt.proyecto.GameActivity
import dev.didnt.proyecto.R
import dev.didnt.proyecto.entidad.Juego

class RvAdapterCategorias: RecyclerView.Adapter<RvAdapterCategorias.MiViewHolder>() {

    private var listaJuegos:ArrayList<Juego> = ArrayList()

    fun agregarDatosCategorias(items: ArrayList<Juego>){
        this.listaJuegos = items
    }
    class MiViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var lblCategory = view.findViewById<TextView>(R.id.lblCategory)
        private var imgCategory = view.findViewById<ImageView>(R.id.imgCategory)

        fun setValoresJuego(juego: Juego){
            lblCategory.text = juego.nombre
            Glide.with(view.context).load(juego.imgUrl).into(imgCategory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_component, parent, false)
        )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val categoriaItem = listaJuegos[position]
        holder.setValoresJuego(categoriaItem)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.view.context, GameActivity::class.java)

            intent.putExtra("id", categoriaItem.id)
            intent.putExtra("nombre", categoriaItem.nombre)
            intent.putExtra("descripcion", categoriaItem.descripcion)
            intent.putExtra("imgUrl", categoriaItem.imgUrl)
            intent.putExtra("fecha_lanzamiento", categoriaItem.fecha_lanzamiento)

            holder.view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaJuegos.size
    }

}