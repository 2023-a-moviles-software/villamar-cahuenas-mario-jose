package modelos

import KLocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate


@Serializable
data class Profesion(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean,
    val salarioPromedio: Double,
    @Serializable(with = KLocalDateSerializer::class) val fechaCreacion: LocalDate,
    val carreras: MutableList<Carrera> = mutableListOf()
){
    val nombresCarreras: List<String>
        get() = carreras.map { it.nombre }
}
object ModeloProfesion {
    private val profesiones: MutableList<Profesion> = mutableListOf()
    private val json = Json { prettyPrint = true }
    private val file = File("profesiones.json")

    fun cargarDatos() {
        if (file.exists()) {
            val jsonString = file.readText()
            profesiones.clear()
            profesiones.addAll(json.decodeFromString(jsonString))
        }else {
            file.createNewFile()
        }
    }

    fun guardarDatos() {
        val jsonString = json.encodeToString(profesiones)
        file.writeText(jsonString)
    }

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
