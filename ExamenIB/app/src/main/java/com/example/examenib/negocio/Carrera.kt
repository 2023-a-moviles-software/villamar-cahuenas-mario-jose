package com.example.examenib.negocio

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