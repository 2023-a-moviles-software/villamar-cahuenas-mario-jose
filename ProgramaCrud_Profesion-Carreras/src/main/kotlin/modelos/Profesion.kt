package modelos

import java.time.LocalDate

data class Profesion(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean,
    val salarioPromedio: Double,
    val fechaCreacion: LocalDate,
    val carreras: MutableList<Carrera> = mutableListOf()
){
    val nombresCarreras: List<String>
        get() = carreras.map { it.nombre }
}
object ModeloProfesion {
    private val profesiones: MutableList<Profesion> = mutableListOf()

    fun obtenerTodos(): List<Profesion> {
        return profesiones.toList()
    }

    fun crear(profesion: Profesion) {
        profesiones.add(profesion)
    }

    fun eliminar(id: Int) {
        val profesion = obtenerPorId(id)
        profesiones.remove(profesion)
    }

    fun obtenerPorId(id: Int): Profesion? {
        return profesiones.find { it.id == id }
    }

    fun actualizar(profesionActualizada: Profesion) {
        val indice = profesiones.indexOfFirst { it.id == profesionActualizada.id }
        if (indice != -1) {
            profesiones[indice] = profesionActualizada
        }
    }
    fun agregarCarrera(profesionId: Int, carrera: Carrera) {
        val profesion = obtenerPorId(profesionId)
        profesion?.carreras?.add(carrera)
    }

    fun eliminarCarrera(profesionId: Int, carreraId: Int) {
        val profesion = obtenerPorId(profesionId)
        profesion?.carreras?.removeIf { it.id == carreraId }
    }
}
