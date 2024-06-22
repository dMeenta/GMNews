package dev.didnt.proyecto.servicio

import com.google.gson.annotations.SerializedName
import dev.didnt.proyecto.entidad.Juego

data class JuegoResponse (
    @SerializedName("listaJuegos") var listaJuegos:ArrayList<Juego>
)