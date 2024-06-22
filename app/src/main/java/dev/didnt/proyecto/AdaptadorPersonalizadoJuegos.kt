package dev.didnt.proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.didnt.proyecto.entidad.Juego

class AdaptadorPersonalizadoJuegos: RecyclerView.Adapter<AdaptadorPersonalizadoJuegos.MiViewHolder>() {

    private var listaJuegos:ArrayList<Juego> = ArrayList()

    fun agregarDatosCategorias(items: ArrayList<Juego>){
        this.listaJuegos = items
    }
    class MiViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var lblCategory = view.findViewById<TextView>(R.id.lblCategory)
        private var imgCategory = view.findViewById<ImageView>(R.id.imgCategory)

        fun setValoresJuego(juego: Juego){
            lblCategory.text = juego.Nombre
            Glide.with(view.context).load(juego.Imagen).into(imgCategory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AdaptadorPersonalizadoJuegos.MiViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_component, parent, false)
        )

    override fun onBindViewHolder(holder: AdaptadorPersonalizadoJuegos.MiViewHolder, position: Int) {
        val categoriaItem = listaJuegos[position]
        holder.setValoresJuego(categoriaItem)
    }

    override fun getItemCount(): Int {
        return listaJuegos.size
    }

}