package vistas

import modelos.Carrera
import modelos.ModeloCarrera
import modelos.Profesion
import modelos.ModeloProfesion
import java.time.LocalDate
import java.time.format.DateTimeParseException


class ProfesionesVista {
    private val tabla = TablaPorConsola()

    fun mostrarMenuPrincipal(menuPrincipal: VistaPrincipal) {
        var opcion: Int
        do {
            println(tabla.crearTablaConTexto("Menú de Profesiones"))
            println("------1. Listar Profesiones")
            println("------2. Agregar Profesion")
            println("------3. Actualizar Profesion")
            println("------4. Eliminar Profesion")
            println("------5. Agregar Carrera a Profesion")
            println("------6. Volver al Menú Principal")
            println("------7. Salir")
            print("> Ingrese una opción: ")

            opcion = readLine()?.toIntOrNull() ?: 0
            when (opcion) {
                1 -> {
                    mostrarProfesiones()
                }
                2 -> {
                    agregarProfesion()
                    mostrarProfesiones()
                }
                3 -> {
                    mostrarProfesiones()
                    actualizarProfesion()
                    mostrarProfesiones()
                }
                4 -> {
                    mostrarProfesiones()
                    eliminarProfesion()
                    mostrarProfesiones()

                }
                5 -> {

                    agregarCarreraAProfesion()
                }
                6-> {
                    println("Volviendo al Menú Principal...")
                    menuPrincipal.iniciar()
                }
                7 -> {
                    println("¡Hasta pronto!")
                    System.exit(0)
                }
                else -> println("Opción no válida")
            }
        } while (opcion != 6)
    }

    private fun mostrarProfesiones() {
        val profesiones = ModeloProfesion.obtenerTodos()

        if (profesiones.isNotEmpty()) {
            println("\n--- Listado de Profesiones ---")
            mostrarTablaProfesiones(profesiones)
        } else {
            println("No hay profesiones registradas.")
        }
    }

    fun mostrarTablaProfesiones(profesiones: List<Profesion>) {
        val titulosProfesiones = listOf("ID", "Nombre", "Descripción", "Activa", "Salario Promedio", "Fecha Creación", "Carreras")
        val datosProfesiones = profesiones.map { profesion ->
            listOf(
                profesion.id.toString(),
                profesion.nombre,
                profesion.descripcion,
                profesion.activa.toString(),
                profesion.salarioPromedio.toString(),
                profesion.fechaCreacion.toString(),
                profesion.nombresCarreras.joinToString(",")
            )
        }

        val tablaProfesiones = tabla.crearTabla(titulosProfesiones, datosProfesiones)
        println(tablaProfesiones)
    }


    private fun agregarProfesion() {
        println(tabla.crearTablaConTexto("--------------Agregar Profesión--------------"))

        print("Ingrese el nombre de la profesión: ")
        val nombre = readLine()

        print("Ingrese la descripción de la profesión: ")
        val descripcion = readLine()

        print("La profesión está activa? (si/no): ")
        val activa = ingresarBooleano()

        print("Ingrese el salario promedio de la profesión: ")
        val salarioPromedio = ingresarDouble()

        print("Ingrese la fecha de creación de la profesión (AAAA-MM-DD): ")
        val fechaCreacion = ingresarFecha()

        val id = generarIdProfesion()

        if (nombre != null && descripcion != null && activa != null && salarioPromedio != null && fechaCreacion != null) {
            val profesion = Profesion(id, nombre, descripcion, activa, salarioPromedio, fechaCreacion)
            ModeloProfesion.crear(profesion)
            println("Profesión agregada exitosamente con el ID: $id")
        } else {
            println("Error al ingresar los datos de la profesión.")
        }
    }

    private fun ingresarBooleano(): Boolean? {
        while (true) {
            val input = readLine()?.lowercase()
            when (input) {
                "si" -> return true
                "no" -> return false
                else -> println("Valor inválido. Ingrese 'si' o 'no'.")
            }
        }
    }

    private fun ingresarDouble(): Double? {
        while (true) {
            val input = readLine()?.toDoubleOrNull()
            if (input != null) {
                return input
            } else {
                println("Valor inválido. Ingrese un número válido.")
            }
        }
    }

    private fun ingresarFecha(): LocalDate? {
        while (true) {
            val input = readLine()
            try {
                return LocalDate.parse(input)
            } catch (e: DateTimeParseException) {
                println("Fecha inválida. Ingrese una fecha en el formato AAAA-MM-DD.")
            }
        }
    }

    private fun generarIdProfesion(): Int {
        val profesiones = ModeloProfesion.obtenerTodos()
        return if (profesiones.isNotEmpty()) {
            profesiones.maxByOrNull { it.id }!!.id + 1
        } else {
            1
        }
    }

    private fun actualizarProfesion() {
        println("\n--- Actualizar Profesion ---")

        print("Ingrese el ID de la profesión que desea actualizar: ")
        val id = readLine()?.toIntOrNull()

        if (id != null) {
            val profesion = ModeloProfesion.obtenerPorId(id)
            if (profesion != null) {
                print("Ingrese el nuevo nombre de la profesión: ")
                val nombre = readLine()

                print("Ingrese la nueva descripción de la profesión: ")
                val descripcion = readLine()

                print("La profesión está activa? (si/no): ")
                val activa = ingresarBooleano()

                print("Ingrese el nuevo salario promedio de la profesión: ")
                val salarioPromedio = ingresarDouble()

                print("Ingrese la nueva fecha de creación de la profesión (AAAA-MM-DD): ")
                val fechaCreacion = ingresarFecha()

                if (nombre != null && descripcion != null && activa != null && salarioPromedio != null && fechaCreacion != null) {
                    val profesionActualizada = Profesion(id, nombre, descripcion, activa, salarioPromedio, fechaCreacion)
                    ModeloProfesion.actualizar(profesionActualizada)
                    println("Profesión actualizada exitosamente.")
                } else {
                    println("Error al ingresar los datos de actualización de la profesión.")
                }
            } else {
                println("No existe una profesión con el ID especificado.")
            }
        } else {
            println("Error al ingresar el ID de la profesión.")
        }
    }


    private fun eliminarProfesion() {
        println("\n--- Eliminar Profesion ---")
        print("Ingrese el ID de la profesión que desea eliminar: ")
        val id = readLine()?.toIntOrNull()

        if (id != null) {
            val profesion = ModeloProfesion.obtenerPorId(id)
            if (profesion != null) {
                ModeloProfesion.eliminar(id)
                println("Profesión eliminada exitosamente.")
            } else {
                println("No existe una profesión con el ID especificado.")
            }
        } else {
            println("Error al ingresar el ID de la profesión.")
        }
    }

    private fun agregarCarreraAProfesion() {
        println("--- Agregar Carrera a Profesión ---")

        val profesiones = ModeloProfesion.obtenerTodos()
        if (profesiones.isNotEmpty()) {
            println("Lista de Profesiones:")
            for (profesion in profesiones) {
                println("${profesion.id} - ${profesion.nombre}")
            }

            print("Ingrese el ID de la profesión a la cual desea agregar una carrera: ")
            val profesionId = readLine()?.toIntOrNull()

            if (profesionId != null) {
                val profesion = ModeloProfesion.obtenerPorId(profesionId)
                if (profesion != null) {
                    val carreras = ModeloCarrera.obtenerTodos()
                    if (carreras.isNotEmpty()) {
                        println("Lista de Carreras:")
                        for (carrera in carreras) {
                            println("${carrera.id} - ${carrera.nombre}")
                        }

                        print("Ingrese el ID de la carrera que desea agregar a la profesión: ")
                        val carreraId = readLine()?.toIntOrNull()

                        if (carreraId != null) {
                            val carrera = ModeloCarrera.obtenerPorId(carreraId)
                            if (carrera != null) {
                                carrera.profesionId = profesionId
                                ModeloProfesion.agregarCarrera(profesionId, carrera)
                                println("Carrera agregada exitosamente a la profesión.")
                            } else {
                                println("No existe una carrera con el ID especificado.")
                            }
                        } else {
                            println("Error al ingresar el ID de la carrera.")
                        }
                    } else {
                        println("No hay carreras disponibles. Debe crear una nueva carrera.")
                    }
                } else {
                    println("No existe una profesión con el ID especificado.")
                }
            } else {
                println("Error al ingresar el ID de la profesión.")
            }
        } else {
            println("No hay profesiones disponibles. Debe crear una nueva profesión.")
            agregarProfesion()
        }
    }

}

