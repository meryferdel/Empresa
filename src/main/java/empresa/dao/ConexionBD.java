package empresa.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abre conexiones con la base de datos MariaDB. Los datos de conexión (url,
 * usuario y clave) se leen del fichero <code>bd.properties</code>, que está en
 * <code>src/main/resources</code>, para no tenerlos escritos en el código.
 *
 * @author Meryb
 * @version 1.0
 */
public final class ConexionBD {

    /** Fichero con los datos de conexión, dentro del classpath. */
    private static final String FICHERO = "/bd.properties";

    /** Datos de conexión ya leídos (se cargan la primera vez que se necesitan). */
    private static Properties propiedades;

    /**
     * Constructor privado: la clase solo tiene métodos estáticos.
     */
    private ConexionBD() {
    }

    /**
     * Lee el fichero de propiedades y carga el conector de MariaDB, solo la
     * primera vez.
     *
     * @return datos de conexión
     * @throws SQLException si falta el fichero o el conector
     */
    private static synchronized Properties cargar() throws SQLException {
        if (propiedades == null) {
            Properties leidas = new Properties();
            try (InputStream entrada = ConexionBD.class.getResourceAsStream(FICHERO)) {
                if (entrada == null) {
                    throw new SQLException("No se encuentra el fichero " + FICHERO);
                }
                leidas.load(new InputStreamReader(entrada, StandardCharsets.UTF_8));
            } catch (IOException ex) {
                throw new SQLException("No se puede leer el fichero " + FICHERO, ex);
            }
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                throw new SQLException("Falta el conector JDBC de MariaDB", ex);
            }
            propiedades = leidas;
        }
        return propiedades;
    }

    /**
     * Abre una conexión nueva con la base de datos.
     *
     * @return conexión abierta (quien la pide debe cerrarla)
     * @throws SQLException si no se puede conectar
     */
    public static Connection getConexion() throws SQLException {
        Properties datos = cargar();
        return DriverManager.getConnection(datos.getProperty("url"),
                datos.getProperty("usuario"), datos.getProperty("clave", ""));
    }
}
