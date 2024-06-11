package dev.didnt.proyecto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.didnt.proyecto.entidad.Noticia

class AdaptadorPersonalizado: RecyclerView.Adapter<AdaptadorPersonalizado.MiViewHolder>() {

    private var listaNoticias:ArrayList<Noticia> = ArrayList()

    fun agregarDatos(items: ArrayList<Noticia>){
        this.listaNoticias = items
    }

    class MiViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var newsTitle = view.findViewById<TextView>(R.id.newsTitle)
        private var newsContent = view.findViewById<TextView>(R.id.newsContent)
        private var newsGame = view.findViewById<TextView>(R.id.newsGame)

        fun setValores(noticia: Noticia){
            newsTitle.text = noticia.title
            newsContent.text = noticia.content
            newsGame.text = noticia.game
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_component,parent,false)
    )

    override fun onBindViewHolder(holder: AdaptadorPersonalizado.MiViewHolder, position: Int) {
        val noticiaItem = listaNoticias[position]
        holder.setValores(noticiaItem)
    }

    override fun getItemCount(): Int {
        return listaNoticias.size
    }
}