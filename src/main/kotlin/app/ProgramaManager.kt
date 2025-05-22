package es.prog2425.calcBD.app


import es.prog2425.calcBD.ui.IEntradaSalida

/**
 * Clase encargada de gestionar el programa principal de la aplicación.
 *
 * @property ui Interfaz de entrada y salida con el usuario.
 * @property menuLogs Controlador que maneja la lógica del modo log.
 * @property menuBd Manejador de base de datos que guarda y opera cálculos.
 * @property args Argumentos de línea de comandos pasados al programa.
 */
class ProgramaManager(
    private val ui: IEntradaSalida,
    private val menuLogs: Controlador,
    private val menuBd: BDManager,
    private val args: Array<String>
) {

    /**
     * Función principal que controla el flujo del programa.
     *
     * Muestra un menú interactivo al usuario y ejecuta la opción correspondiente.
     */
    fun programa(){
        var salir = false

        while (!salir) {
            ui.limpiarPantalla(10)
            mostrarMenu()
            ui.saltoLinea()
            val entrada = ui.pedirInfo("Elige una opción (o escribe): ")
            when (entrada){
                "1", "log" -> menuLogs.iniciar(args)
                "2", "bd" -> menuBd.programaBd()
                "3", "salir" -> salir = salirPrograma()
                else -> ui.mostrarError("Opción inválida")
            }
        }
    }

    /**
     * Muestra el menú principal de opciones al usuario en consola.
     */
    private fun mostrarMenu(){
        ui.mostrar("""
            ----MENÚ PROGRAMA----
            1. Calculadora Log (log)
            2. Calculadora BD (bd)
            3. Salir
        """.trimIndent())
    }

    /**
     * Marca la bandera de salida y muestra un mensaje de cierre del programa.
     */
    private fun salirPrograma(): Boolean{
        ui.saltoLinea()
        ui.mostrar("Saliendo del programa...")
        return true
    }
}