package com.frankz.examen_2b_mario.negocio

data class Carrera(
    val id: String,
    var nombre: String,
    var descripcion: String,
    var activa: Boolean,
    var duracion: Int,
    var profesionId: String
){
    override fun toString(): String {
        return nombre
    }
}