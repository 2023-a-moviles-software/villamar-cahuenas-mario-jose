import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQLiteManager(private val dbName: String) {
    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:$dbName")

    init {
        createTables()
    }

    private fun createTables() {
        val createProfesionTable = """
            CREATE TABLE IF NOT EXISTS Profesion (
                id INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                descripcion TEXT,
                activa BOOLEAN NOT NULL,
                salarioPromedio REAL NOT NULL,
                fechaCreacion TEXT NOT NULL
            );
        """.trimIndent()

        val createCarreraTable = """
            CREATE TABLE IF NOT EXISTS Carrera (
                id INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                descripcion TEXT,
                activa BOOLEAN NOT NULL,
                duracion INTEGER NOT NULL,
                profesionId INTEGER NOT NULL,
                FOREIGN KEY (profesionId) REFERENCES Profesion(id)
            );
        """.trimIndent()

        connection.createStatement().apply {
            executeUpdate(createProfesionTable)
            executeUpdate(createCarreraTable)
            close()
        }
    }

    fun close() {
        connection.close()
    }

    fun executeQuery(query: String, block: (PreparedStatement) -> Unit, resultSetHandler: (ResultSet) -> Unit) {
        connection.prepareStatement(query).use { statement ->
            block(statement)
            val resultSet = statement.executeQuery()
            resultSetHandler(resultSet)
        }
    }

    fun executeUpdate(query: String, block: (PreparedStatement) -> Unit): Int {
        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS).use { statement ->
            block(statement)
            val affectedRows = statement.executeUpdate()

            val generatedKeys = statement.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            }

            return affectedRows
        }
    }
}

