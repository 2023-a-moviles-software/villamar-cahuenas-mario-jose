package modelos
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Carrera(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean,
    val duracion: Int,
    var profesionId: Int

)

object ModeloCarrera {
    private val carreras: MutableList<Carrera> = mutableListOf()
    private val json = Json { prettyPrint = true }
    private val file = File("carreras.json")

    //finciones para cargar los datos desde los archivos
    fun cargarDatos() {
        if (file.exists()) {
            val jsonString = file.readText()
            carreras.clear()
            carreras.addAll(json.decodeFromString(jsonString))
        } else {
            file.createNewFile()
        }
    }
    fun guardarDatos() {
        val jsonString = json.encodeToString(carreras)
        file.writeText(jsonString)
    }

    fun obtenerTodos(): List<Carrera> {
        return carreras.toList()
    }

    fun crear(carrera: Carrera) {
        carreras.add(carrera)
    }

    fun eliminar(id: Int) {
        val carrera = obtenerPorId(id)
        carreras.remove(carrera)
    }

    fun obtenerPorId(id: Int): Carrera? {
        return carreras.find { it.id == id }
    }

    fun actualizar(carreraActualizada: Carrera) {
        val indice = carreras.indexOfFirst { it.id == carreraActualizada.id }
        if (indice != -1) {
            carreras[indice] = carreraActualizada
        }
    }

    fun obtenerCarrerasPorProfesion(profesionId: Int): List<Carrera> {
        return carreras.filter { it.profesionId == profesionId }
    }
}
