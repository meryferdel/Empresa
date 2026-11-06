package empresa.servlet;

import empresa.dao.EmpleadoDAO;
import empresa.modelo.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Opción 1 del menú: consulta la base de datos y muestra todos los empleados
 * (nombre, DNI, sexo, categoría y años trabajados) en una página JSP.
 *
 * @author Meryb
 * @version 1.0
 */
@WebServlet(name = "ListarEmpleadosServlet", urlPatterns = {"/listar"})
public class ListarEmpleadosServlet extends BaseServlet {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea el servlet (lo instancia el servidor).
     */
    public ListarEmpleadosServlet() {
    }

    /**
     * Consulta todos los empleados y los pasa a la página <code>listado.jsp</code>.
     *
     * @param peticion  petición recibida
     * @param respuesta respuesta que se enviará
     * @throws ServletException si falla el reenvío a la página
     * @throws IOException      si falla la salida
     */
    @Override
    protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        try {
            List<Empleado> empleados = new EmpleadoDAO().listar();
            peticion.setAttribute("empleados", empleados);
            reenviar(peticion, respuesta, "/WEB-INF/jsp/listado.jsp");
        } catch (SQLException ex) {
            errorBaseDatos(peticion, respuesta, ex);
        }
    }
}
