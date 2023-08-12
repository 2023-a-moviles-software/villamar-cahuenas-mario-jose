package modelos


import SQLiteManager
import java.time.LocalDate


data class Profesion(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val activa: Boolean,
    val salarioPromedio: Double,
    val fechaCreacion: LocalDate,

) {

    private val sqliteManager = SQLiteManager("entidades.db")

    val nombresCarreras: List<String>
        get() {
            val query = "SELECT nombre FROM Carrera WHERE profesionId = ?;"
            val nombres = mutableListOf<String>()

            sqliteManager.executeQuery(query, { statement ->
                statement.setInt(1, id)
            }) { resultSet ->
                while (resultSet.next()) {
                    nombres.add(resultSet.getString("nombre"))
                }
            }

            return nombres
        }

}

object ModeloProfesion {
    private val sqliteManager = SQLiteManager("entidades.db")


    fun obtenerTodos(): List<Profesion> {
        val query = "SELECT * FROM Profesion;"
        val profesiones = mutableListOf<Profesion>()

        sqliteManager.executeQuery(query, { statement ->
            val resultSet = statement.executeQuery()
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val nombre = resultSet.getString("nombre")
                val descripcion = resultSet.getString("descripcion")
                val activa = resultSet.getBoolean("activa")
                val salarioPromedio = resultSet.getDouble("salarioPromedio")
                val fechaCreacion = resultSet.getString("fechaCreacion")
                val fechaCreacionLocalDate = LocalDate.parse(fechaCreacion)
                val carreras = ModeloCarrera.obtenerCarrerasPorProfesion(id)
                val profesion = Profesion(id, nombre, descripcion, activa, salarioPromedio, fechaCreacionLocalDate)

                profesiones.add(profesion)
            }
        }) { resultSet ->
            // Handler para el resultado del ResultSet, no es necesario hacer nada aquí.
        }

        return profesiones
    }



    fun crear(profesion: Profesion) {
        val query = "INSERT INTO Profesion (nombre, descripcion, activa, salarioPromedio, fechaCreacion) VALUES (?, ?, ?, ?, ?);"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setString(1, profesion.nombre)
            statement.setString(2, profesion.descripcion)
            statement.setBoolean(3, profesion.activa)
            statement.setDouble(4, profesion.salarioPromedio)
            statement.setString(5, profesion.fechaCreacion.toString())
        }
    }

    fun eliminar(id: Int) {
        val query = "DELETE FROM Profesion WHERE id = ?;"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setInt(1, id)
        }
    }

    fun obtenerPorId(id: Int): Profesion? {
        val query = "SELECT * FROM Profesion WHERE id = ?;"
        var profesion: Profesion? = null

        sqliteManager.executeQuery(query, { statement ->
            statement.setInt(1, id)
        }) { resultSet ->
            if (resultSet.next()) {
                val nombre = resultSet.getString("nombre")
                val descripcion = resultSet.getString("descripcion")
                val activa = resultSet.getBoolean("activa")
                val salarioPromedio = resultSet.getDouble("salarioPromedio")
                val fechaCreacion = resultSet.getString("fechaCreacion")
                val fechaCreacionLocalDate = LocalDate.parse(fechaCreacion)

                profesion = Profesion(id, nombre, descripcion, activa, salarioPromedio, fechaCreacionLocalDate)
            }
        }

        return profesion
    }


    fun actualizar(profesionActualizada: Profesion) {
        val query = "UPDATE Profesion SET nombre = ?, descripcion = ?, activa = ?, salarioPromedio = ?, fechaCreacion = ? WHERE id = ?;"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setString(1, profesionActualizada.nombre)
            statement.setString(2, profesionActualizada.descripcion)
            statement.setBoolean(3, profesionActualizada.activa)
            statement.setDouble(4, profesionActualizada.salarioPromedio)
            statement.setString(5, profesionActualizada.fechaCreacion.toString())
            statement.setInt(6, profesionActualizada.id)
        }
    }

    fun agregarCarrera(profesionId: Int, carrera: Carrera) {
        val query = "INSERT INTO Carrera (nombre, descripcion, activa, duracion, profesionId) VALUES (?, ?, ?, ?, ?);"

        sqliteManager.executeUpdate(query) { statement ->
            statement.setString(1, carrera.nombre)
            statement.setString(2, carrera.descripcion)
            statement.setBoolean(3, carrera.activa)
            statement.setInt(4, carrera.duracion)
            statement.setInt(5, profesionId)
        }
    }


}


