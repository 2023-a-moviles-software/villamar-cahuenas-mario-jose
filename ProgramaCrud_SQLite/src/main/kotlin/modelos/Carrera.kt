package modelos

import SQLiteManager

data class Carrera(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean,
    val duracion: Int,
    var profesionId: Int

)
object ModeloCarrera {
    private val sqliteManager = SQLiteManager("entidades.db")

    fun crear(carrera: Carrera) {
        val query = "INSERT INTO Carrera (nombre, descripcion, activa, duracion, profesionId) VALUES (?, ?, ?, ?, ?);"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setString(1, carrera.nombre)
            statement.setString(2, carrera.descripcion)
            statement.setBoolean(3, carrera.activa)
            statement.setInt(4, carrera.duracion)
            statement.setInt(5, carrera.profesionId)
        }
    }

    fun obtenerTodos(): List<Carrera> {
        val query = "SELECT * FROM Carrera;"
        val carreras = mutableListOf<Carrera>()

        sqliteManager.executeQuery(query, {}) { resultSet ->
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val nombre = resultSet.getString("nombre")
                val descripcion = resultSet.getString("descripcion")
                val activa = resultSet.getBoolean("activa")
                val duracion = resultSet.getInt("duracion")
                val profesionId = resultSet.getInt("profesionId")

                carreras.add(Carrera(id, nombre, descripcion, activa, duracion, profesionId))
            }
        }

        return carreras
    }

    fun obtenerPorId(id: Int): Carrera? {
        val query = "SELECT * FROM Carrera WHERE id = ?;"
        var carrera: Carrera? = null

        sqliteManager.executeQuery(query, { statement ->
            statement.setInt(1, id)
        }) { resultSet ->
            if (resultSet.next()) {
                val nombre = resultSet.getString("nombre")
                val descripcion = resultSet.getString("descripcion")
                val activa = resultSet.getBoolean("activa")
                val duracion = resultSet.getInt("duracion")
                val profesionId = resultSet.getInt("profesionId")

                carrera = Carrera(id, nombre, descripcion, activa, duracion, profesionId)
            }
        }

        return carrera
    }

    fun actualizar(carreraActualizada: Carrera) {
        val query = "UPDATE Carrera SET nombre = ?, descripcion = ?, activa = ?, duracion = ?, profesionId = ? WHERE id = ?;"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setString(1, carreraActualizada.nombre)
            statement.setString(2, carreraActualizada.descripcion)
            statement.setBoolean(3, carreraActualizada.activa)
            statement.setInt(4, carreraActualizada.duracion)
            statement.setInt(5, carreraActualizada.profesionId)
            statement.setInt(6, carreraActualizada.id)
        }
    }

    fun eliminar(id: Int) {
        val query = "DELETE FROM Carrera WHERE id = ?;"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setInt(1, id)
        }
    }

    fun obtenerCarrerasPorProfesion(profesionId: Int): List<Carrera> {
        val query = "SELECT * FROM Carrera WHERE profesionId = ?;"
        val carreras = mutableListOf<Carrera>()

        sqliteManager.executeQuery(query, { statement ->
            statement.setInt(1, profesionId)
        }) { resultSet ->
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val nombre = resultSet.getString("nombre")
                val descripcion = resultSet.getString("descripcion")
                val activa = resultSet.getBoolean("activa")
                val duracion = resultSet.getInt("duracion")
                val profesionId = resultSet.getInt("profesionId")

                carreras.add(Carrera(id, nombre, descripcion, activa, duracion, profesionId))
            }
        }

        return carreras
    }

}
