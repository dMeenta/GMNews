package dev.didnt.proyecto.servicio

import com.google.gson.annotations.SerializedName
import dev.didnt.proyecto.entidad.Noticia

data class NoticiaResponse (
    @SerializedName("listaNoticia") var listaNoticias:ArrayList<Noticia>
)