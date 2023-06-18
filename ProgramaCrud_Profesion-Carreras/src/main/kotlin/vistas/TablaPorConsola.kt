package vistas

class TablaPorConsola {
    private val horizontal = "─"
    private val vertical = "│"
    private val cornerTopLeft = "┌"
    private val cornerTopRight = "┐"
    private val cornerBottomLeft = "└"
    private val cornerBottomRight = "┘"

    fun crearTablaConTexto(text: String): String {
        val tableWidth = text.length + 2
        val horizontalLine = horizontal.repeat(tableWidth)
        val topLine = "$cornerTopLeft$horizontalLine$cornerTopRight"
        val middleLine = "$vertical $text $vertical"
        val bottomLine = "$cornerBottomLeft$horizontalLine$cornerBottomRight"

        return "$topLine\n$middleLine\n$bottomLine"
    }

    fun <T> crearTabla(titulos: List<String>, datos: List<List<T>>): String {
        val columnas = obtenerColumnas(titulos, datos)

        val filas = mutableListOf<String>()
        filas.add(crearFila(titulos, columnas))
        filas.add(obtenerSeparador(columnas))
        for (filaDatos in datos) {
            filas.add(crearFila(filaDatos.map { it.toString() }, columnas))
        }

        return filas.joinToString("\n")
    }

    private fun obtenerColumnas(titulos: List<String>, datos: List<List<*>>): List<Int> {
        val columnas = titulos.map { it.length }.toMutableList()
        for (fila in datos) {
            for ((indice, valor) in fila.withIndex()) {
                val longitudValor = valor.toString().length
                if (columnas.size <= indice) {
                    columnas.add(longitudValor)
                } else if (longitudValor > columnas[indice]) {
                    columnas[indice] = longitudValor
                }
            }
        }
        return columnas
    }

    private fun crearFila(valores: List<String>, columnas: List<Int>): String {
        val filaAjustada = ajustarLongitudFilas(valores, columnas)
        return filaAjustada.joinToString(" $vertical ")
    }

    private fun ajustarLongitudFilas(fila: List<String>, columnas: List<Int>): List<String> {
        return fila.mapIndexed { indice, valor ->
            valor.padEnd(columnas[indice])
        }
    }

    private fun obtenerSeparador(columnas: List<Int>): String {
        val separador = StringBuilder()
        for (longitudColumna in columnas) {
            separador.append(horizontal.repeat(longitudColumna)).append("$horizontal$horizontal")
        }
        return separador.toString().dropLast(1)
    }
}
