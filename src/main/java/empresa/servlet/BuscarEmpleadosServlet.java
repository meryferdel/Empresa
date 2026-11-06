package empresa.servlet;

import empresa.dao.EmpleadoDAO;
import empresa.modelo.Empleado;
import empresa.modelo.FiltroBusqueda;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Opción 3 del menú: recibe los criterios del formulario de búsqueda (por
 * cualquiera de los datos del empleado), consulta la base de datos y muestra
 * los empleados encontrados en una página JSP, desde donde se puede pasar a
 * modificar cada uno.
 *
 * @author Meryb
 * @version 1.0
 */
@WebServlet(name = "BuscarEmpleadosServlet", urlPatterns = {"/buscar"})
public class BuscarEmpleadosServlet extends BaseServlet {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea el servlet (lo instancia el servidor).
     */
    public BuscarEmpleadosServlet() {
    }

    /** Página con el formulario de búsqueda. */
    private static final String FORMULARIO = "/jsp/formularioBusqueda.jsp";

    /**
     * Valida los criterios, busca los empleados y reenvía a
     * <code>resultadosBusqueda.jsp</code>. Si algún dato no es válido, vuelve
     * al formulario con la lista de errores.
     *
     * @param peticion  petición recibida (dni, nombre, sexo, categoria, anyos, sueldo)
     * @param respuesta respuesta que se enviará
     * @throws ServletException si falla el reenvío a la página
     * @throws IOException      si falla la salida
     */
    @Override
    protected void doGet(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        FiltroBusqueda filtro = new FiltroBusqueda();
        filtro.setDni(texto(peticion.getParameter("dni")));
        filtro.setNombre(texto(peticion.getParameter("nombre")));

        String sexo = texto(peticion.getParameter("sexo"));
        if (sexo != null && !sexo.equals("M") && !sexo.equals("F")) {
            errores.add("El sexo debe ser M o F.");
            sexo = null;
        }
        filtro.setSexo(sexo);

        Integer categoria = leerEntero(peticion, "categoria", "Categoría", errores);
        if (categoria != null && (categoria < 1 || categoria > 10)) {
            errores.add("La categoría debe estar entre 1 y 10.");
            categoria = null;
        }
        filtro.setCategoria(categoria);
        filtro.setAnyos(leerEntero(peticion, "anyos", "Años trabajados", errores));
        filtro.setSueldo(leerEntero(peticion, "sueldo", "Sueldo", errores));

        if (!errores.isEmpty()) {
            peticion.setAttribute("errores", errores);
            reenviar(peticion, respuesta, FORMULARIO);
            return;
        }
        try {
            List<Empleado> empleados = new EmpleadoDAO().buscar(filtro);
            peticion.setAttribute("empleados", empleados);
            peticion.setAttribute("sinCriterios", filtro.estaVacio());
            reenviar(peticion, respuesta, "/WEB-INF/jsp/resultadosBusqueda.jsp");
        } catch (SQLException ex) {
            errorBaseDatos(peticion, respuesta, ex);
        }
    }
}
