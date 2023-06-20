package vistas
import modelos.Carrera
import modelos.ModeloCarrera
import modelos.ModeloProfesion

class CarrerasVista {
    private val tabla = TablaPorConsola()

    fun mostrarMenuPrincipal(menuPrincipal: VistaPrincipal) {
        var opcion: Int
        do {
            println(tabla.crearTablaConTexto("Menú de Carreras"))
            println("------1. Listar Carreras")
            println("------2. Agregar Carrera")
            println("------3. Actualizar Carrera")
            println("------4. Eliminar Carrera")
            println("------5. Volver al Menú Principal")
            println("------6. Salir")
            print("> Ingrese una opción: ")

            opcion = readLine()?.toIntOrNull() ?: 0
            when (opcion) {
                1 -> {
                    println("\n1. Listar todas las Carreras")
                    println("2. Listar Carreras por Profesión")
                    val listarOpcion = readLine()?.toIntOrNull() ?: 0
                    when (listarOpcion) {
                        1 -> mostrarCarreras()
                        2 -> mostrarCarrerasPorProfesion()
                        else -> println("Opción no válida")
                    }
                }
                2 -> {
                    mostrarCarreras()
                    agregarCarrera()
                    mostrarCarreras()
                }
                3 -> {
                    mostrarCarreras()
                    actualizarCarrera()
                    mostrarCarreras()
                }
                4 -> {
                    mostrarCarreras()
                    eliminarCarrera()
                    mostrarCarreras()
                }
                5-> {
                    ModeloCarrera.guardarDatos() // Guardar los datos antes de regresar
                    ModeloProfesion.guardarDatos()
                    println("Volviendo al Menú Principal...")
                    menuPrincipal.iniciar()
                }
                6 -> {
                    ModeloCarrera.guardarDatos() // Guardar los datos antes de salir de la aplicación
                    ModeloProfesion.guardarDatos()
                    println("¡Hasta pronto!")
                    System.exit(0)
                }
                else -> println("Opción no válida")
            }
        } while (opcion != 5)
    }

    private fun mostrarCarreras() {
        val carreras = ModeloCarrera.obtenerTodos()

        if (carreras.isNotEmpty()) {
            println("\n--- Listado de Carreras ---")
            mostrarTablaCarreras(carreras)
        } else {
            println("No hay carreras registradas.")
        }
    }

    private fun mostrarCarrerasPorProfesion() {
        val profesiones = ModeloProfesion.obtenerTodos()
        if (profesiones.isNotEmpty()) {
            println("Lista de Profesiones:")
            for (profesion in profesiones) {
                println("${profesion.id} - ${profesion.nombre}")
            }
        } else {
            println("No hay profesiones registradas.")
            return
        }

        print("Ingrese el ID de la profesión: ")
        val profesionId = readLine()?.toIntOrNull()

        if (profesionId != null) {
            val carreras = ModeloCarrera.obtenerCarrerasPorProfesion(profesionId)

            if (carreras.isNotEmpty()) {
                println("\n--- Listado de Carreras de la Profesión $profesionId ---")
                mostrarTablaCarreras(carreras)
            } else {
                println("No hay carreras registradas para la profesión $profesionId.")
            }
        } else {
            println("Error al ingresar el ID de la profesión.")
        }
    }

    private fun mostrarTablaCarreras(carreras: List<Carrera>) {
        val titulosCarreras = listOf("ID", "Nombre", "Descripción", "Activa", "Duración", "ID Profesión")
        val datosCarreras = carreras.map { carrera ->
            listOf(
                carrera.id.toString(),
                carrera.nombre,
                carrera.descripcion,
                carrera.activa.toString(),
                carrera.duracion.toString(),
                carrera.profesionId.toString()
            )
        }

        val tablaCarreras = tabla.crearTabla(titulosCarreras, datosCarreras)
        println(tablaCarreras)
    }

    private fun generarIdCarrera(): Int {
        val carreras = ModeloCarrera.obtenerTodos()
        return if (carreras.isNotEmpty()) {
            carreras.maxByOrNull { it.id }!!.id + 1
        } else {
            1
        }
    }
    private fun agregarCarrera() {
        println("\n--- Agregar Carrera ---")

        print("Ingrese el nombre de la carrera: ")
        val nombre = readLine()

        print("Ingrese la descripción de la carrera: ")
        val descripcion = readLine()

        print("La carrera está activa? (si/no): ")
        val activaString = readLine()

        val activa = activaString?.equals("si", ignoreCase = true) ?: false

        print("Ingrese la duración de la carrera (en años): ")
        val duracion = readLine()?.toIntOrNull()

        val profesionId = -1
        val id = generarIdCarrera()

        if (nombre != null && descripcion != null && activa != null && duracion != null) {
            val carrera = Carrera(id, nombre, descripcion, activa, duracion, profesionId)
            ModeloCarrera.crear(carrera)
            println("Carrera agregada exitosamente con el ID: ${carrera.id}")
        } else {
            println("Error al ingresar los datos de la carrera.")
        }
    }

    private fun actualizarCarrera() {
        println("\n--- Actualizar Carrera ---")

        print("Ingrese el ID de la carrera que desea actualizar: ")
        val id = readLine()?.toIntOrNull()

        if (id != null) {
            val carreraExistente = ModeloCarrera.obtenerPorId(id)
            if (carreraExistente != null) {
                print("Ingrese el nuevo nombre de la carrera: ")
                val nombre = readLine()

                print("Ingrese la nueva descripción de la carrera: ")
                val descripcion = readLine()

                print("La carrera está activa? (si/no): ")
                val activaString = readLine()

                val activa = activaString?.equals("si", ignoreCase = true) ?: false


                print("Ingrese la nueva duración de la carrera (en años): ")
                val duracion = readLine()?.toIntOrNull()

                val profesionId = carreraExistente.profesionId

                if (nombre != null && descripcion != null && activa != null && duracion != null && profesionId != null) {
                    val carreraActualizada = Carrera(id, nombre, descripcion, activa, duracion, profesionId)
                    ModeloCarrera.actualizar(carreraActualizada)
                    println("Carrera actualizada exitosamente.")
                } else {
                    println("Error al ingresar los datos de actualización de la carrera.")
                }
            } else {
                println("No existe una carrera con el ID especificado.")
            }
        } else {
            println("Error al ingresar el ID de la carrera.")
        }
    }

    private fun eliminarCarrera() {
        println("\n--- Eliminar Carrera ---")
        print("Ingrese el ID de la carrera que desea eliminar: ")
        val id = readLine()?.toIntOrNull()

        if (id != null) {
            val carrera = ModeloCarrera.obtenerPorId(id)
            if (carrera != null) {
                ModeloCarrera.eliminar(id)
                println("Carrera eliminada exitosamente.")
            } else {
                println("No existe una carrera con el ID especificado.")
            }
        } else {
            println("Error al ingresar el ID de la carrera.")
        }
    }
}

