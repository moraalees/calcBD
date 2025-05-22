package es.prog2425.calcBD.model

/**
 * Representa una operación matemática realizada entre dos números.
 *
 * @property numero1 Primer valor.
 * @property numero2 Segundo valor.
 * @property operador Símbolo de la operación.
 * @property resultado Resultado de aplicar la operación a los números.
 */
data class Calculo(
    val numero1: Double,
    val numero2: Double,
    val operador: Operador,
    val resultado: Double
) {
    /**
     * Representación en texto del cálculo con un formato específico.
     */
    override fun toString(): String {
        return "%.2f %s %.2f = %.2f".format(numero1, operador.simboloUi, numero2, resultado)
    }
}