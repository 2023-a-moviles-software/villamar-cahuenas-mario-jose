package com.example.examenib.negocio



class BaseDeDatosMemoria {
//
//    companion object{
//        val arregloProfesion = arrayListOf<Profesion>()
//        private var lastId = 0
//        init {
//            arregloProfesion.add(
//                Profesion(
//                     id = 1,
//                    "Ingeniería",
//                    "Ciencias exactas y ciencias multidisciplinarias",
//                    true,
//                    1000.00,
//                    carreras = arrayListOf(
//                        Carrera(
//                            id = 1,
//                            nombre = "Ingeniería en Sistemas",
//                            descripcion = "Ingeniería en Sistemas y ciecias de la computación",
//                            activa = true,
//                            duracion = 10,
//                            profesionId = 1
//                        ),
//                        Carrera(
//                            id = 2,
//                            nombre = "Ingeniería en Mecatrónica",
//                            descripcion = "Ingeniería en Mecatrónica",
//                            activa = true,
//                            duracion = 10,
//                            profesionId = 1
//                        )
//                    )
//                )
//            )
//
//            arregloProfesion.add(
//                Profesion(
//                    id = 2,
//                    "Medicina",
//                    "Ciencas de la salud y ciencias naturales",
//                    true,
//                    1000.00,
//                    carreras = arrayListOf(
//                        Carrera(
//                            id = 3,
//                            nombre = "Enfermería",
//                            descripcion = "Ciencias de la salud y ciencias naturales",
//                            activa = true,
//                            duracion = 10,
//                            profesionId = 2
//                        )
//                    )
//                )
//            )
//            arregloProfesion.add(
//                Profesion(
//                    id = 3,
//                    "Finanzas",
//                    "Ciencias sociales y ciencias exactas",
//                    true,
//                    1000.00,
//                    carreras = arrayListOf(
//                        Carrera(
//                            id = 4,
//                            nombre = "Contabilidad",
//                            descripcion = "Cinecias sociales y ciencias exactas",
//                            activa = true,
//                            duracion = 10,
//                            profesionId = 3
//                        )
//                    )
//                )
//            )
//            lastId = arregloProfesion.size
//        }
//
//        fun obtenerProfesiones(): ArrayList<Profesion> {
//            return arregloProfesion
//        }
//        fun andirCarrera(carrera: Carrera){
//            val profesion = obtenerProfesionPorId(carrera.profesionId)
//            profesion?.carreras?.add(carrera)
//        }
//        fun obtenerProfesionPorId(id: Int): Profesion? {
//            return arregloProfesion
//                .firstOrNull {
//                    it.id == id
//                }
//        }
//        fun crearProfesion(
//            nombre: String,
//            descripcion: String,
//            activa: Boolean,
//            salarioPromedio: Double
//        ): Boolean {
//            val existeProfesion = arregloProfesion.any {
//                it.nombre == nombre
//            }
//            if (!existeProfesion) {
//                lastId++
//                arregloProfesion.add(
//                    Profesion(
//                        lastId,
//                        nombre,
//                        descripcion,
//                        activa,
//                        salarioPromedio
//                    )
//                )
//            }
//            return !existeProfesion
//        }
//        fun actualizarProfesion(
//            id: Int,
//            nombre: String,
//            descripcion: String,
//            activa: Boolean,
//            salarioPromedio: Double
//        ): Boolean {
//            val indiceProfesion = arregloProfesion.indexOfFirst {
//                it.id == id
//            }
//            if (indiceProfesion == -1) {
//                return false
//            }
//            arregloProfesion[indiceProfesion] = Profesion(
//                id,
//                nombre,
//                descripcion,
//                activa,
//                salarioPromedio
//            )
//            return true
//        }
//
//        fun eliminarProfesionPorId(id: Int): Boolean {
//            obtenerProfesionPorId(id)?.let {
//                arregloProfesion.remove(it)
//                return true
//            }
//            return false
//        }
//
//        // metodos para carreras
//        fun obtenerCarreraPorId(idCarrera: Int,idProfesion: Int): Carrera? {
//            //obtener la profesion
//            val profesion = obtenerProfesionPorId(idProfesion)
//            //obtener la carrera de la profesion
//            return profesion?.carreras?.firstOrNull {
//                it.id == idCarrera
//            }
//        }
//
//        fun crearCarrera(
//            nombre: String,
//            descripcion: String,
//            activa: Boolean,
//            duracion: Int,
//            profesionId: Int
//        ): Boolean {
//            val existeCarrera = arregloProfesion
//                .flatMap {
//                    it.carreras
//                }
//                .any {
//                    it.nombre == nombre
//                }
//            if (!existeCarrera) {
//                val profesion = obtenerProfesionPorId(profesionId)
//                profesion?.carreras?.add(
//                    Carrera(
//                        lastId,
//                        nombre,
//                        descripcion,
//                        activa,
//                        duracion,
//                        profesionId
//                    )
//                )
//                lastId++
//            }
//            return !existeCarrera
//        }
//        fun actualizarCarrera(
//            id: Int,
//            nombre: String,
//            descripcion: String,
//            activa: Boolean,
//            duracion: Int,
//            profesionId: Int
//        ): Boolean {
//            val indiceCarrera = arregloProfesion
//                .flatMap {
//                    it.carreras
//                }
//                .indexOfFirst {
//                    it.id == id
//                }
//            if (indiceCarrera == -1) {
//                return false
//            }
//            val profesion = obtenerProfesionPorId(profesionId)
//            profesion?.carreras?.set(
//                indiceCarrera,
//                Carrera(
//                    id,
//                    nombre,
//                    descripcion,
//                    activa,
//                    duracion,
//                    profesionId
//                )
//            )
//            return true
//        }
//        fun eliminarCarreraPorId(idC: Int,idP:Int): Boolean {
//            val carrera = obtenerCarreraPorId(idC,idP)
//            if (carrera != null) {
//                val profesion = obtenerProfesionPorId(idP)
//                profesion?.carreras?.remove(carrera)
//                return true
//            }
//            return false
//        }
//        fun obtenerCarrerasPorProfesionId(profesionId: Int): List<Carrera> {
//            val profesion = obtenerProfesionPorId(profesionId)
//            return profesion?.carreras ?: arrayListOf()
//        }
//        fun obtenerCarreras(): List<Carrera> {
//            return arregloProfesion
//                .flatMap {
//                    it.carreras
//                }
//        }
//
//    }
}