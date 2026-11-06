package empresa.modelo;

/**
 * Criterios de la búsqueda de empleados. Cada campo es opcional: los que
 * valen <code>null</code> no se tienen en cuenta, y los que se rellenan se
 * combinan todos a la vez (el empleado debe cumplirlos todos).
 *
 * @author Meryb
 * @version 1.0
 */
public class FiltroBusqueda {

    /** Texto que debe contener el DNI. */
    private String dni;

    /** Texto que debe contener el nombre. */
    private String nombre;

    /** Sexo exacto ("M" o "F"). */
    private String sexo;

    /** Categoría exacta. */
    private Integer categoria;

    /** Años trabajados exactos. */
    private Integer anyos;

    /** Sueldo exacto. */
    private Integer sueldo;

    /**
     * Crea un filtro sin ningún criterio.
     */
    public FiltroBusqueda() {
    }

    /**
     * Indica si el filtro no tiene ningún criterio (se buscarían todos).
     *
     * @return <code>true</code> si todos los campos están vacíos
     */
    public boolean estaVacio() {
        return dni == null && nombre == null && sexo == null
                && categoria == null && anyos == null && sueldo == null;
    }

    /**
     * Devuelve el texto buscado en el DNI.
     *
     * @return texto del DNI, o <code>null</code>
     */
    public String getDni() {
        return dni;
    }

    /**
     * Cambia el texto buscado en el DNI.
     *
     * @param dni texto del DNI
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve el texto buscado en el nombre.
     *
     * @return texto del nombre, o <code>null</code>
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el texto buscado en el nombre.
     *
     * @param nombre texto del nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el sexo buscado.
     *
     * @return "M", "F" o <code>null</code>
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Cambia el sexo buscado.
     *
     * @param sexo "M", "F" o <code>null</code>
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Devuelve la categoría buscada.
     *
     * @return categoría, o <code>null</code>
     */
    public Integer getCategoria() {
        return categoria;
    }

    /**
     * Cambia la categoría buscada.
     *
     * @param categoria categoría, o <code>null</code>
     */
    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    /**
     * Devuelve los años trabajados buscados.
     *
     * @return años trabajados, o <code>null</code>
     */
    public Integer getAnyos() {
        return anyos;
    }

    /**
     * Cambia los años trabajados buscados.
     *
     * @param anyos años trabajados, o <code>null</code>
     */
    public void setAnyos(Integer anyos) {
        this.anyos = anyos;
    }

    /**
     * Devuelve el sueldo buscado.
     *
     * @return sueldo, o <code>null</code>
     */
    public Integer getSueldo() {
        return sueldo;
    }

    /**
     * Cambia el sueldo buscado.
     *
     * @param sueldo sueldo, o <code>null</code>
     */
    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }
}
