import java.util.*

fun main(args: Array<String>) {
    // variables inmutables: no se puede reasignar su valor

    val inmutable: String = "Adrian";
    //inmutable = "Vicente";

    //Mutables: si se pueden reasignar
    var mutable: String = "Vicente";
    mutable = "Adrian";

    //Dock Typing
    var ejemploVariable = "Mario Villamar";
    val edadEjemplo: Int = 12;

    //Variable primitiva
    val nombreProfesor: String = "Adrian Eguez";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'C';
    val mayorDeEdad: Boolean = true;
    // Clases Java
    val fechaNacimiento: Date = Date();

    // Switch
    val estadoCivilWhen = "C";
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado");
        }
        "S" -> {
            println("Soltero");
        }
        else -> {
            println("No sabemos!");
        }
    }
    val esSoltero = (estadoCivilWhen == "S");
    val coqueto = if(esSoltero) "Si" else "No";

    //Funciones
    // el void -> Unit
    fun imprimirNombre(nombre:String):Unit{
        println("Nombre :  ${nombre}") /// template strings
    }
    fun calcularSueldo(
        sueldo:Double,//Requerido
        tasa: Double = 12.0,//Opcional (por defecto)
        bonoEspecial: Double?=null//Opcion null -> nullable
    ):Double{
        return if(bonoEspecial == null){
            sueldo * (100/tasa);
        }else {
            sueldo * (100/tasa) + bonoEspecial;
        }
    }
    calcularSueldo(10.00);
    calcularSueldo(10.00,12.00,20.00);
    calcularSueldo(10.00, bonoEspecial = 20.0);// patametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 10.00);

    //abstract class
    abstract class NumerosJava{ //Constructor Primario
        //Ejmplo:
        //uno:Int , (Parametro (sin modificar acceso) )
        //private var  uno: Int // Propiedad Publica Clase numeros.uno
        // var uno: Int // Propiedad de la clase (por Defecto es PUBLIC)
        // public var uno: Int,

        protected val numeroUno: Int; //Propieda de la clase protected numeros.numeroUno
        private val numeroDos: Int //Propiedad de la clase private numeros.numeroDos

        //var cedula: String = "" (es publica por defecto)
        //private varCalculado: Int = 0 (private )
        constructor( //bloque constructor primario
            uno:Int,
            dos:Int
        ){
            this.numeroUno = uno;// this es opcional
            numeroDos = dos;//
            println("Inicializando");
        }

    }
    class Suma(
        unoParametro: Int,
        dosParametro: Int,
    ): NumerosJava(uno, dos){
        init {
            this.numeroUno
            this.numeroDos
        }
        constructor(
            uno: Int?,
            dos: Int
        ): this(if(uno == null) 0 else uno, dos)

        constructor(
            uno: Int,
            dos: Int?
        ): this(uno, if(dos == null) 0 else dos)
        }
        public fun sumar(): Int{
            val total: Int = this.numeroUno + this.numeroDos;
            return total;
        }
        companion object{
            val pi = 3.14
            fun elevarAlCuadrado(numero: Int): Int{
                return numero * numero;
            }
        val historialSumas = ArrayLsit<Int>();
        fun agregarHistorial(numero: Int){
            historialSumas.add(numero);
        }
    }




    //
}