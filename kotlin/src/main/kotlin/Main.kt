import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("Hello world");
    val inmutable: String = "Mario Villmar";
    //inmutable = "Leonardo";
    var mutable: String = "MArio";
    mutable = "Jose";
    //Duck Typing
    var ejmploVariable = "Adrian";
    val edadEjemplo: Int = 20;

    //Clases JAVA
    val fechaNacimiento: Date = Date();

    val estadoCivil = "C";
    when (estadoCivil) {
        ("C") -> {
            println("Casado");
        }
        ("S") -> {
            println("Soltero");
        }
        else -> {
            println("NO sabemos!");
        }
    }
    val esSoltero = (estadoCivil == "S");
    var coqueteo = if (esSoltero) "Si" else "No";

    calcularSueldo(10.00);
    calcularSueldo(10.00, 12.00, 20.00);
    calcularSueldo(10.00, bonoEspecial = 20.00);
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.0, tasa = 14.0);
    val sumaUno = Suma(1,2);
    val sumaDos = Suma(null,2);
    val sumaTres = Suma(1,null);
    val sumCuatro = Suma(null,null);
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)


    //------------------------------------------Arreglos estaticos--------------------
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3,4);
    println(arregloEstatico);




    //-------------------------------------------Arreglos DInamicos-------------------------
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4);
    println(arregloDinamico);

    //operadores -> sirven para arreglos dinamicos

    //FOR EACH ->   Itera el arreglo -> UNIT
    val respuestaForEach: Unit = arregloEstatico
        .forEach { valorIteracion: Int ->
            println("Valor iteracion: ${valorIteracion}");
        }
    arregloDinamico.forEach { println(it) } // it es el valor iterado

    println(respuestaForEach);

    arregloEstatico.forEachIndexed { indice: Int, valorIteracion: Int ->
        println("Valor ${valorIteracion} Indice: ${indice}");
    }

    //Map -> Muta el arreglo (Cambia el arreglo) -> Arreglo
    //1) Enviemos el nuevo valor de la iteracion
    //2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 1000.00;
        }
    var respuestaMapDos = arregloDinamico.map { it + 15.00 }
    println(respuestaMap);


    //Filter -> FILTRAR EL ARREGLO
    //1) Devolver una expresion (TRUE o FALSE)
    //2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5;
            return@filter mayoresACinco;
        }
    val respuestaFilterDos = arregloDinamico.filter { it > 5 };
    println(respuestaFilter);
    println(respuestaFilterDos);

    // OR -> ANY (ALGUNO CUMPLE) -> Boolean
    // AND -> ALL (TODOS CUMPLEN) -> Boolean

    val respuestaAny:Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any valorActual > 5;
        }
    val respuestaAnyDos:Boolean = arregloDinamico.any { it > 5 };
    println(respuestaAny);

    val respuestaALL: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all valorActual > 5;
        }
    println(respuestaALL)

    //REDUCE -> VALOR ACUMULADO
    //1) Devuelve el acumulado
    //2) Valor acumulado = 0 (Siempre es 0 )
    // [1,2,3,4,5] -> Sumar todas los valores del arreglo
    // valorIteracion1 = valorEmpienza + 1 = 0 + 1 = 1 -> Iteracion 1
    // valorIteracion2 = valor + 2 = 1 + 2 = 3 -> Iteracion 2
    // valorIteracion3 = valor + 3 = 3 + 3 = 6 -> Iteracion 3
    // valorIteracion4 = valor + 4 = 6 + 4 = 10 -> Iteracion 4
    // valorIteracion5 = valor + 5 = 10 + 5 = 15 -> Iteracion 5

    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            return@reduce acumulado + valorActual;// -> Logica de reduccion
        }
    println(respuestaReduce);




}

fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}");
}
fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100/tasa);
    } else {
        return sueldo * (100/tasa) + bonoEspecial;
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int;
    protected val numeroDos: Int;
    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializando");
    }
}

abstract class Numeros(
    protected val numeroUno: Int,
    protected val numeroDos: Int,
) {
    init {
        this.numeroUno;
        this.numeroDos;
        numeroUno;
        numeroDos;
        println("Inicializando");
    }
}

class Suma (
    unoParametro: Int,
    dosParametro: Int
) : Numeros(unoParametro, dosParametro) //Extendiendo y mandando parametros
{
    init {
        this.numeroUno;
        this.numeroDos;
    }
    constructor(
        uno: Int?,
        dos: Int
    ): this(
        if( uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,
        dos: Int?
    ): this(
        uno,
        if( dos == null) 0 else dos,
    )

    constructor(
        uno: Int?,
        dos: Int?
    ): this(
        if( uno == null) 0 else uno,
        if( dos == null) 0 else dos,
    )

    public fun sumar(): Int {
        val total = numeroUno + numeroDos;
        agregarHistorial(total);
        return total;
    }
    companion object { //Static
        val pi = 3.14;
        fun elevarAlCuadrado(num: Int): Int {
            return num * num;
        }
        val historialSumas = ArrayList<Int>();
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma);
        }
    }



}