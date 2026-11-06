package empresa.servlet;

import empresa.dao.EmpleadoDAO;
import empresa.modelo.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Opción 2 del menú: servlet controlador que recibe el DNI del formulario,
 * consulta en la base de datos el salario de ese empleado y lo muestra en una
 * página JSP.
 *
 * @author Meryb
 * @version 1.0
 */
@WebServlet(name = "SalarioServlet", urlPatterns = {"/salario"})
public class SalarioServlet extends BaseServlet {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea el servlet (lo instancia el servidor).
     */
    public SalarioServlet() {
    }

    /** Página con el formulario donde se escribe el DNI. */
    private static final String FORMULARIO = "/jsp/formularioSalario.jsp";

    /**
     * Busca el empleado por su DNI y reenvía a <code>salario.jsp</code>. Si el
     * DNI está vacío, vuelve al formulario con un mensaje de error.
     *
     * @param peticion  petición recibida (parámetro <code>dni</code>)
     * @param respuesta respuesta que se enviará
     * @throws ServletException si falla el reenvío a la página
     * @throws IOException      si falla la salida
     */
    @Override
    protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String dni = texto(peticion.getParameter("dni"));
        if (dni == null) {
            peticion.setAttribute("error", "Escribe el DNI del empleado.");
            reenviar(peticion, respuesta, FORMULARIO);
            return;
        }
        dni = dni.toUpperCase();
        try {
            Empleado empleado = new EmpleadoDAO().buscarPorDni(dni);
            peticion.setAttribute("dniBuscado", dni);
            peticion.setAttribute("empleado", empleado);
            reenviar(peticion, respuesta, "/WEB-INF/jsp/salario.jsp");
        } catch (SQLException ex) {
            errorBaseDatos(peticion, respuesta, ex);
        }
    }
}
