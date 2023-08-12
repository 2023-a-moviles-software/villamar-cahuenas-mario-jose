import modelos.ModeloCarrera
import modelos.ModeloProfesion
import vistas.VistaPrincipal

fun main(args: Array<String>) {

    val dbName = "entidades.db"  // Cambia el nombre a tu preferencia
    val sqliteManager = SQLiteManager(dbName)

    VistaPrincipal().iniciar()

    // cerrar la conexion a la base de datos
    sqliteManager.close()

}