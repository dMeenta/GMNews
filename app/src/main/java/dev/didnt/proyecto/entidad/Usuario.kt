package dev.didnt.proyecto.entidad

class Usuario {
    var uid:String? = null
    var nombre:String? = null
    var idOnline:String? = null
    var genero:String? = null
    var fechaNacimiento:String? = null
    var email:String? = null
    var edad:Int? = null

    constructor(){}

    constructor(uid:String?, nombre:String?, idOnline:String?, genero:String?, fechaNacimiento:String?, email:String?, edad:Int?){
        this.uid = uid
        this.nombre = nombre
        this.idOnline = idOnline
        this.genero = genero
        this.fechaNacimiento = fechaNacimiento
        this.email = email
        this.edad = edad
    }
}