package com.example.examenib.negocio

data class Profesion(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    var activa: Boolean,
    var salarioPromedio: Double,
    val carreras: MutableList<Carrera> = mutableListOf()
){
    override fun toString(): String {
        return nombre
    }
}