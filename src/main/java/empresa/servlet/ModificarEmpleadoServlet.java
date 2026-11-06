package empresa.servlet;

import empresa.dao.EmpleadoDAO;
import empresa.modelo.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Modificación de un empleado (ampliación opcional del ejercicio). Con GET
 * muestra el formulario de edición del empleado indicado; con POST valida los
 * datos y los guarda. El DNI identifica al empleado y no se puede cambiar. El
 * sueldo es un campo calculado: no se pide en el formulario y se recalcula
 * automáticamente a partir de la categoría y los años trabajados.
 *
 * @author Meryb
 * @version 1.0
 */
@WebServlet(name = "ModificarEmpleadoServlet", urlPatterns = {"/modificar"})
public class ModificarEmpleadoServlet extends BaseServlet {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /**
     * Crea el servlet (lo instancia el servidor).
     */
    public ModificarEmpleadoServlet() {
    }

    /** Página con el formulario de edición. */
    private static final String PAGINA_EDITAR = "/WEB-INF/jsp/editar.jsp";

    /** Longitud máxima del nombre (la de la columna de la base de datos). */
    private static final int LONGITUD_MAXIMA_NOMBRE = 100;

    /**
     * Muestra el formulario de edición con los datos actuales del empleado.
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
            mostrarError(peticion, respuesta, HttpServletResponse.SC_BAD_REQUEST,
                    "Falta el DNI", "No se ha indicado qué empleado se quiere modificar.");
            return;
        }
        try {
            Empleado empleado = new EmpleadoDAO().buscarPorDni(dni);
            if (empleado == null) {
                noEncontrado(peticion, respuesta, dni);
                return;
            }
            peticion.setAttribute("empleado", empleado);
            reenviar(peticion, respuesta, PAGINA_EDITAR);
        } catch (SQLException ex) {
            errorBaseDatos(peticion, respuesta, ex);
        }
    }

    /**
     * Valida los datos del formulario y, si son correctos, los guarda y
     * recalcula el sueldo. En cualquier caso vuelve a mostrar el formulario,
     * con un mensaje de confirmación o con la lista de errores.
     *
     * @param peticion  petición recibida (dni, nombre, sexo, categoria, anyos)
     * @param respuesta respuesta que se enviará
     * @throws ServletException si falla el reenvío a la página
     * @throws IOException      si falla la salida
     */
    @Override
    protected void doPost(HttpServletRequest peticion, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String dni = texto(peticion.getParameter("dni"));
        if (dni == null) {
            mostrarError(peticion, respuesta, HttpServletResponse.SC_BAD_REQUEST,
                    "Falta el DNI", "No se ha indicado qué empleado se quiere modificar.");
            return;
        }
        try {
            EmpleadoDAO dao = new EmpleadoDAO();
            Empleado original = dao.buscarPorDni(dni);
            if (original == null) {
                noEncontrado(peticion, respuesta, dni);
                return;
            }

            // Se parte de los datos guardados y se sustituye cada campo que sea válido
            List<String> errores = new ArrayList<>();
            Empleado editado = new Empleado(original.getDni(), original.getNombre(),
                    original.getSexo(), original.getCategoria(), original.getAnyos(),
                    original.getSueldo());

            String nombre = texto(peticion.getParameter("nombre"));
            if (nombre == null) {
                errores.add("El nombre no puede estar vacío.");
            } else if (nombre.length() > LONGITUD_MAXIMA_NOMBRE) {
                errores.add("El nombre no puede tener más de " + LONGITUD_MAXIMA_NOMBRE
                        + " caracteres.");
            } else {
                editado.setNombre(nombre);
            }

            String sexo = texto(peticion.getParameter("sexo"));
            if ("M".equals(sexo) || "F".equals(sexo)) {
                editado.setSexo(sexo);
            } else {
                errores.add("El sexo debe ser M o F.");
            }

            int erroresAntes = errores.size();
            Integer categoria = leerEntero(peticion, "categoria", "Categoría", errores);
            if (categoria == null) {
                if (errores.size() == erroresAntes) {
                    errores.add("La categoría es obligatoria.");
                }
            } else if (categoria < 1 || categoria > 10) {
                errores.add("La categoría debe estar entre 1 y 10.");
            } else {
                editado.setCategoria(categoria);
            }

            erroresAntes = errores.size();
            Integer anyos = leerEntero(peticion, "anyos", "Años trabajados", errores);
            if (anyos == null) {
                if (errores.size() == erroresAntes) {
                    errores.add("Los años trabajados son obligatorios.");
                }
            } else {
                editado.setAnyos(anyos);
            }

            peticion.setAttribute("empleado", editado);
            if (!errores.isEmpty()) {
                peticion.setAttribute("errores", errores);
            } else if (dao.actualizar(editado)) {
                peticion.setAttribute("mensajeOk", "Los datos se han guardado correctamente. "
                        + "El sueldo se ha recalculado automáticamente.");
            } else {
                noEncontrado(peticion, respuesta, dni);
                return;
            }
            reenviar(peticion, respuesta, PAGINA_EDITAR);
        } catch (SQLException ex) {
            errorBaseDatos(peticion, respuesta, ex);
        }
    }

    /**
     * Muestra la página de error cuando no existe el empleado pedido.
     *
     * @param peticion  petición actual
     * @param respuesta respuesta actual
     * @param dni       DNI que no se ha encontrado
     * @throws ServletException si falla el reenvío
     * @throws IOException      si falla la salida
     */
    private void noEncontrado(HttpServletRequest peticion, HttpServletResponse respuesta,
            String dni) throws ServletException, IOException {
        mostrarError(peticion, respuesta, HttpServletResponse.SC_NOT_FOUND,
                "Empleado no encontrado",
                "No existe ningún empleado con el DNI " + dni + ".");
    }
}
