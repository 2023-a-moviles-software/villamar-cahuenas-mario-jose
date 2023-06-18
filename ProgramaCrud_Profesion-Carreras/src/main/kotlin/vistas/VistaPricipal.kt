package vistas

import modelos.ModeloCarrera
import modelos.ModeloProfesion


class VistaPrincipal {
    private val tablas = TablaPorConsola()
    private val vistaProfesion = ProfesionesVista()
    private val vistaCarrera = CarrerasVista()

    fun iniciar() {
        ModeloCarrera.cargarDatos() // Cargar los datos al inicio de la aplicación
        ModeloProfesion.cargarDatos()
        mostrarEncabezado()
        seleccionarMenuEntidades()

    }

    fun mostrarEncabezado() {
        val tablaConTitulo = tablas.crearTablaConTexto("Bienvenido a la Aplicación de Gestión de Profesiones y Carreras")
        println(tablaConTitulo)
    }

    fun seleccionarMenuEntidades() {
        var mostrarNuevamente = false
        do {
            println("Seleccione una opción:")
            println("1. Profesiones")
            println("2. Carreras")
            println("3. Salir")
            print("> Ingrese una opción: ")

            val opcion = readLine()?.toIntOrNull()
            when (opcion) {
                1 -> vistaProfesion.mostrarMenuPrincipal(this);
                2 -> vistaCarrera.mostrarMenuPrincipal(this);
                3 -> {
                    ModeloCarrera.guardarDatos() // Guardar los datos antes de salir de la aplicación
                    ModeloProfesion.guardarDatos()
                    println("¡Vuelva pronto!")
                    mostrarNuevamente = false
                }
                else -> {
                    println("Opción no válida")
                    mostrarNuevamente = true
                }
            }
        } while (mostrarNuevamente)
    }
}

