package es.prog2425.calcBD.model

/**
 * Enumeración de operadores matemáticos admitidos por la aplicación.
 *
 * @property simboloUi Carácter representativo del operador para mostrar en la UI.
 * @property simbolos Lista de caracteres reconocidos como equivalentes al operador.
 */
enum class Operador(val simboloUi: Char, val simbolos: List<Char>) {
    SUMA('+', listOf('+')),
    RESTA('-', listOf('-')),
    MULTIPLICACION('x', listOf('*', 'x')),
    DIVISION('/', listOf(':', '/'));

    companion object {
        /**
         * Obtiene el operador correspondiente a un Char.
         *
         * @param operador Carácter que representa una operación matemática.
         * @return Operador correspondiente si se encuentra, si no, null.
         */
        fun getOperador(operador: Char?) = operador?.let { op -> entries.find { op in it.simbolos } }
    }
}