package com.example.movilessoftware2023a
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqLiteHelperEntrenador(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """ 
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearEntrenador(
        nombre:String,
        descripcion:String
    ):Boolean{
        val baseDatosEscritura  = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)
        val resultadoGuardar = baseDatosEscritura.insert(
            "ENTRENADOR",
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() === -1) false else true
    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "ENTRENADOR",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormuIario(
        nombre: String,
        descripcion: String,
        id:Int,
    ) : Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActuatizar = ContentValues()
        valoresAActuatizar.put("nombre",nombre)
        valoresAActuatizar.put("descripcion",descripcion)
// where = ?
        val parametrosConsultaActuaIizar = arrayOf( id. toString() )
        val resultadoActua1izacion= conexionEscritura
            .update(
                "ENTRENADOR", // Nombr•e tabla
                valoresAActuatizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActuaIizar
            )
        conexionEscritura. close()
        return if(resultadoActua1izacion.toInt() == -1) false else true
    }

    fun consultarEntrenadorporId(id:Int): BEntrenador{
        val baseDatosLectura = readableDatabase
        val scripCOnsultaLectura =  """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scripCOnsultaLectura,
            parametrosConsultaLectura
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0, "", "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion = resultadoConsultaLectura.getString(2)
                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion =descripcion
                }
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

}


