package com.frankz.examen_2b_mario.negocio

data class Profesion(
    val id: String,
    var nombre: String,
    var descripcion: String,
    var activa: Boolean,
    var salarioPromedio: Double
){
    override fun toString(): String {
        return nombre
    }
}