package modelos
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
