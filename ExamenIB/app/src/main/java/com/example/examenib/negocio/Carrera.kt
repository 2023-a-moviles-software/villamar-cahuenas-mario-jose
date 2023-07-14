package com.example.examenib.negocio

data class Carrera(
    val id: Int,
    var nombre: String,
    var descripcion: String,
    var activa: Boolean,
    var duracion: Int,
    var profesionId: Int

){
    override fun toString(): String {
        return nombre
    }
}