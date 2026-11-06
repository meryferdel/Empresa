package empresa.modelo;

/**
 * Cálculo del sueldo de un empleado, igual que en el ejercicio de nóminas del
 * primer curso: sueldo base de su categoría más 5000 por cada año trabajado.
 * El sueldo es un campo calculado, por eso nunca se pide al usuario.
 *
 * @author Meryb
 * @version 1.0
 */
public final class CalculoNomina {

    /** Sueldo base según la categoría (posición 0 = categoría 1). */
    private static final int[] SUELDO_BASE = {
        50000, 70000, 90000, 110000, 130000,
        150000, 170000, 190000, 210000, 230000
    };

    /** Plus por cada año trabajado. */
    private static final int PLUS_POR_ANYO = 5000;

    /**
     * Constructor privado: la clase solo tiene métodos estáticos.
     */
    private CalculoNomina() {
    }

    /**
     * Calcula el sueldo.
     *
     * @param categoria categoría del empleado, entre 1 y 10
     * @param anyos     años trabajados, no negativo
     * @return sueldo calculado
     * @throws IllegalArgumentException si la categoría o los años no son válidos
     */
    public static int calcular(int categoria, int anyos) {
        if (categoria < 1 || categoria > SUELDO_BASE.length) {
            throw new IllegalArgumentException("La categoría debe estar entre 1 y 10");
        }
        if (anyos < 0) {
            throw new IllegalArgumentException("Los años trabajados no pueden ser negativos");
        }
        return SUELDO_BASE[categoria - 1] + PLUS_POR_ANYO * anyos;
    }
}
