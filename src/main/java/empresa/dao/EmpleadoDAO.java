package empresa.dao;

import empresa.modelo.CalculoNomina;
import empresa.modelo.Empleado;
import empresa.modelo.FiltroBusqueda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a los datos de los empleados y sus nóminas en la base de datos
 * (tablas <b>Empleados</b>(dni, nombre, sexo, categoria, anyos) y
 * <b>Nominas</b>(dni, sueldo)). Todas las consultas usan sentencias con
 * parámetros, de modo que lo que escribe el usuario nunca se pega dentro del
 * texto SQL.
 *
 * @author Meryb
 * @version 1.0
 */
public class EmpleadoDAO {

    /** Consulta base: cada empleado con su sueldo (si lo tiene). */
    private static final String CONSULTA_BASE
            = "SELECT e.dni, e.nombre, e.sexo, e.categoria, e.anyos, n.sueldo "
            + "FROM Empleados e LEFT JOIN Nominas n ON n.dni = e.dni";

    /** Orden de todos los listados. */
    private static final String ORDEN = " ORDER BY e.nombre, e.dni";

    /**
     * Crea el objeto de acceso a datos.
     */
    public EmpleadoDAO() {
    }

    /**
     * Crea un {@link Empleado} con la fila actual de un resultado.
     *
     * @param rs resultado de la consulta base
     * @return empleado creado
     * @throws SQLException si falla la lectura de alguna columna
     */
    private Empleado crearEmpleado(ResultSet rs) throws SQLException {
        int sueldo = rs.getInt("sueldo");
        Integer sueldoGuardado = rs.wasNull() ? null : sueldo;
        return new Empleado(rs.getString("dni"), rs.getString("nombre"),
                rs.getString("sexo"), rs.getInt("categoria"), rs.getInt("anyos"),
                sueldoGuardado);
    }

    /**
     * Devuelve todos los empleados, ordenados por nombre.
     *
     * @return lista de empleados (vacía si no hay ninguno)
     * @throws SQLException si falla la consulta
     */
    public List<Empleado> listar() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        try (Connection conexion = ConexionBD.getConexion();
                Statement sentencia = conexion.createStatement();
                ResultSet rs = sentencia.executeQuery(CONSULTA_BASE + ORDEN)) {
            while (rs.next()) {
                empleados.add(crearEmpleado(rs));
            }
        }
        return empleados;
    }

    /**
     * Busca un empleado por su DNI exacto.
     *
     * @param dni DNI del empleado
     * @return el empleado, o <code>null</code> si no existe
     * @throws SQLException si falla la consulta
     */
    public Empleado buscarPorDni(String dni) throws SQLException {
        try (Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(CONSULTA_BASE + " WHERE e.dni = ?")) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? crearEmpleado(rs) : null;
            }
        }
    }

    /**
     * Escapa los caracteres que tienen significado especial en un LIKE (el
     * porcentaje, el guion bajo y la barra invertida), para que se busquen
     * tal cual los escribió el usuario.
     *
     * @param texto texto escrito por el usuario
     * @return texto con los caracteres especiales escapados
     */
    private String escaparLike(String texto) {
        return texto.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }

    /**
     * Busca empleados que cumplan todos los criterios rellenados del filtro.
     * El DNI y el nombre buscan texto contenido (sin distinguir mayúsculas);
     * el resto de campos exigen el valor exacto.
     *
     * @param filtro criterios de búsqueda
     * @return empleados encontrados, ordenados por nombre
     * @throws SQLException si falla la consulta
     */
    public List<Empleado> buscar(FiltroBusqueda filtro) throws SQLException {
        StringBuilder sql = new StringBuilder(CONSULTA_BASE).append(" WHERE 1 = 1");
        List<Object> parametros = new ArrayList<>();
        if (filtro.getDni() != null) {
            sql.append(" AND e.dni LIKE ?");
            parametros.add("%" + escaparLike(filtro.getDni()) + "%");
        }
        if (filtro.getNombre() != null) {
            sql.append(" AND e.nombre LIKE ?");
            parametros.add("%" + escaparLike(filtro.getNombre()) + "%");
        }
        if (filtro.getSexo() != null) {
            sql.append(" AND e.sexo = ?");
            parametros.add(filtro.getSexo());
        }
        if (filtro.getCategoria() != null) {
            sql.append(" AND e.categoria = ?");
            parametros.add(filtro.getCategoria());
        }
        if (filtro.getAnyos() != null) {
            sql.append(" AND e.anyos = ?");
            parametros.add(filtro.getAnyos());
        }
        if (filtro.getSueldo() != null) {
            sql.append(" AND n.sueldo = ?");
            parametros.add(filtro.getSueldo());
        }
        sql.append(ORDEN);

        List<Empleado> empleados = new ArrayList<>();
        try (Connection conexion = ConexionBD.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    empleados.add(crearEmpleado(rs));
                }
            }
        }
        return empleados;
    }

    /**
     * Modifica los datos de un empleado (el DNI identifica al empleado y no
     * se cambia). El sueldo no se recibe: se recalcula aquí con la categoría
     * y los años nuevos y se guarda en la tabla Nominas, en la misma
     * transacción, de modo que los datos y el sueldo nunca quedan
     * descuadrados.
     *
     * @param empleado empleado con los datos nuevos; al terminar, su sueldo
     *                 contiene el sueldo recalculado
     * @return <code>true</code> si se modificó; <code>false</code> si no existe
     *         ningún empleado con ese DNI
     * @throws SQLException             si falla la actualización
     * @throws IllegalArgumentException si la categoría o los años no son válidos
     */
    public boolean actualizar(Empleado empleado) throws SQLException {
        int sueldo = CalculoNomina.calcular(empleado.getCategoria(), empleado.getAnyos());
        String sqlEmpleado = "UPDATE Empleados SET nombre = ?, sexo = ?, categoria = ?, "
                + "anyos = ? WHERE dni = ?";
        String sqlNomina = "INSERT INTO Nominas (dni, sueldo) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE sueldo = VALUES(sueldo)";
        try (Connection conexion = ConexionBD.getConexion()) {
            conexion.setAutoCommit(false);
            try (PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado);
                    PreparedStatement psNomina = conexion.prepareStatement(sqlNomina)) {
                psEmpleado.setString(1, empleado.getNombre());
                psEmpleado.setString(2, empleado.getSexo());
                psEmpleado.setInt(3, empleado.getCategoria());
                psEmpleado.setInt(4, empleado.getAnyos());
                psEmpleado.setString(5, empleado.getDni());
                if (psEmpleado.executeUpdate() == 0) {
                    conexion.rollback();
                    return false;
                }
                psNomina.setString(1, empleado.getDni());
                psNomina.setInt(2, sueldo);
                psNomina.executeUpdate();
                conexion.commit();
                empleado.setSueldo(sueldo);
                return true;
            } catch (SQLException ex) {
                conexion.rollback();
                throw ex;
            }
        }
    }
}
