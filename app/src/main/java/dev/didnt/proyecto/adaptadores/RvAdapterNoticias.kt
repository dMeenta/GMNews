package dev.didnt.proyecto.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import dev.didnt.proyecto.R
import dev.didnt.proyecto.entidad.Noticia
import kotlin.random.Random

class RvAdapterNoticias: RecyclerView.Adapter<RvAdapterNoticias.MiViewHolder>() {

    private var listaNoticias:ArrayList<Noticia> = ArrayList()

    fun agregarDatosNoticias(items: ArrayList<Noticia>){
        this.listaNoticias = items
    }

    class MiViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        private var newsTitle = view.findViewById<TextView>(R.id.newsTitle)
        private var newsContent = view.findViewById<TextView>(R.id.newsContent)
        private var newsGame = view.findViewById<TextView>(R.id.newsGame)
        var imgView:ShapeableImageView = view.findViewById<ShapeableImageView>(R.id.imgNew)
        fun setValoresNoticia(noticia: Noticia){
            newsTitle.text = noticia.title
            newsContent.text = noticia.content
            newsGame.text = noticia.game
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_component,parent,false)
    )

    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val noticiaItem = listaNoticias[position]
        holder.setValoresNoticia(noticiaItem)
        val num = Random.nextInt(1, 5)
        when(num){
            1 -> holder.imgView.setImageResource(R.drawable.news_1)
            2 -> holder.imgView.setImageResource(R.drawable.news_2)
            3 -> holder.imgView.setImageResource(R.drawable.news_3)
            4 -> holder.imgView.setImageResource(R.drawable.news_4)
        }
    }

    override fun getItemCount(): Int {
        return listaNoticias.size
    }
}