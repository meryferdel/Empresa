package empresa.modelo;

/**
 * Empleado de la empresa, tal y como está guardado en la base de datos
 * (tablas Empleados y Nominas).
 *
 * @author Meryb
 * @version 1.0
 */
public class Empleado {

    /** DNI del empleado (clave primaria). */
    private String dni;

    /** Nombre completo. */
    private String nombre;

    /** Sexo: "M" o "F". */
    private String sexo;

    /** Categoría, entre 1 y 10. */
    private int categoria;

    /** Años trabajados (no negativo). */
    private int anyos;

    /** Sueldo guardado en la tabla Nominas; <code>null</code> si no tiene nómina. */
    private Integer sueldo;

    /**
     * Crea un empleado vacío.
     */
    public Empleado() {
    }

    /**
     * Crea un empleado con todos sus datos.
     *
     * @param dni       DNI del empleado
     * @param nombre    nombre completo
     * @param sexo      "M" o "F"
     * @param categoria categoría, entre 1 y 10
     * @param anyos     años trabajados
     * @param sueldo    sueldo guardado, o <code>null</code> si no tiene nómina
     */
    public Empleado(String dni, String nombre, String sexo, int categoria,
            int anyos, Integer sueldo) {
        this.dni = dni;
        this.nombre = nombre;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anyos = anyos;
        this.sueldo = sueldo;
    }

    /**
     * Devuelve el DNI.
     *
     * @return DNI del empleado
     */
    public String getDni() {
        return dni;
    }

    /**
     * Cambia el DNI.
     *
     * @param dni nuevo DNI
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve el nombre.
     *
     * @return nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el sexo.
     *
     * @return "M" o "F"
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Cambia el sexo.
     *
     * @param sexo "M" o "F"
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Devuelve la categoría.
     *
     * @return categoría, entre 1 y 10
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * Cambia la categoría.
     *
     * @param categoria nueva categoría, entre 1 y 10
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve los años trabajados.
     *
     * @return años trabajados
     */
    public int getAnyos() {
        return anyos;
    }

    /**
     * Cambia los años trabajados.
     *
     * @param anyos nuevos años trabajados
     */
    public void setAnyos(int anyos) {
        this.anyos = anyos;
    }

    /**
     * Devuelve el sueldo guardado.
     *
     * @return sueldo, o <code>null</code> si el empleado no tiene nómina
     */
    public Integer getSueldo() {
        return sueldo;
    }

    /**
     * Cambia el sueldo (campo calculado: no se edita a mano).
     *
     * @param sueldo nuevo sueldo
     */
    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }
}
