package empresa.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase base de los servlets de la aplicación. Reúne lo que todos necesitan:
 * reenviar a una página JSP, mostrar la página de error y leer los datos de
 * los formularios.
 *
 * @author Meryb
 * @version 1.0
 */
public abstract class BaseServlet extends HttpServlet {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea el servlet base (lo usan las clases hijas).
     */
    protected BaseServlet() {
    }

    /** Página JSP que muestra los mensajes de error. */
    protected static final String PAGINA_ERROR = "/WEB-INF/jsp/error.jsp";

    /**
     * Reenvía la petición a una página JSP.
     *
     * @param peticion  petición actual
     * @param respuesta respuesta actual
     * @param ruta      ruta de la página JSP
     * @throws ServletException si falla el reenvío
     * @throws IOException      si falla la salida
     */
    protected void reenviar(HttpServletRequest peticion, HttpServletResponse respuesta,
            String ruta) throws ServletException, IOException {
        peticion.getRequestDispatcher(ruta).forward(peticion, respuesta);
    }

    /**
     * Muestra la página de error con un título y un mensaje.
     *
     * @param peticion  petición actual
     * @param respuesta respuesta actual
     * @param estado    código HTTP de la respuesta (404, 500...)
     * @param titulo    título de la página de error
     * @param mensaje   explicación para el usuario
     * @throws ServletException si falla el reenvío
     * @throws IOException      si falla la salida
     */
    protected void mostrarError(HttpServletRequest peticion, HttpServletResponse respuesta,
            int estado, String titulo, String mensaje) throws ServletException, IOException {
        respuesta.setStatus(estado);
        peticion.setAttribute("tituloError", titulo);
        peticion.setAttribute("mensajeError", mensaje);
        reenviar(peticion, respuesta, PAGINA_ERROR);
    }

    /**
     * Muestra la página de error por un fallo de la base de datos. El detalle
     * técnico solo se anota en el registro del servidor; al usuario se le da
     * un mensaje general.
     *
     * @param peticion  petición actual
     * @param respuesta respuesta actual
     * @param ex        excepción producida
     * @throws ServletException si falla el reenvío
     * @throws IOException      si falla la salida
     */
    protected void errorBaseDatos(HttpServletRequest peticion, HttpServletResponse respuesta,
            SQLException ex) throws ServletException, IOException {
        log("Error de acceso a la base de datos", ex);
        mostrarError(peticion, respuesta, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error de base de datos",
                "No se ha podido acceder a la base de datos. Comprueba que MariaDB está "
                + "arrancado y que los datos del fichero bd.properties son correctos.");
    }

    /**
     * Quita los espacios de los extremos de un texto.
     *
     * @param valor texto recibido (puede ser <code>null</code>)
     * @return el texto sin espacios, o <code>null</code> si era nulo o estaba vacío
     */
    protected static String texto(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    /**
     * Lee un parámetro que debe ser un número entero no negativo. Si no es
     * válido, anota el error en la lista.
     *
     * @param peticion  petición actual
     * @param parametro nombre del parámetro del formulario
     * @param etiqueta  nombre del campo que se usará en el mensaje de error
     * @param errores   lista donde se anotan los errores
     * @return el número, o <code>null</code> si el campo está vacío o no es válido
     */
    protected static Integer leerEntero(HttpServletRequest peticion, String parametro,
            String etiqueta, List<String> errores) {
        String valor = texto(peticion.getParameter(parametro));
        if (valor == null) {
            return null;
        }
        try {
            int numero = Integer.parseInt(valor);
            if (numero < 0) {
                errores.add(etiqueta + ": no puede ser negativo.");
                return null;
            }
            return numero;
        } catch (NumberFormatException ex) {
            errores.add(etiqueta + ": debe ser un número entero.");
            return null;
        }
    }
}
