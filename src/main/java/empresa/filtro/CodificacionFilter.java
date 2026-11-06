package empresa.filtro;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filtro que se aplica a todas las peticiones de la aplicación y fija la
 * codificación UTF-8 antes de leer los datos de los formularios. Sin él, los
 * nombres con tildes o eñes llegarían mal a los servlets.
 *
 * @author Meryb
 * @version 1.0
 */
@WebFilter(filterName = "CodificacionFilter", urlPatterns = {"/*"})
public class CodificacionFilter implements Filter {

    /**
     * Crea el filtro (lo instancia el servidor).
     */
    public CodificacionFilter() {
    }

    /**
     * Fija UTF-8 en la petición y en la respuesta y continúa con la cadena.
     *
     * @param peticion  petición recibida
     * @param respuesta respuesta que se enviará
     * @param cadena    resto de filtros y destino final
     * @throws IOException      si falla la entrada o la salida
     * @throws ServletException si falla el servlet de destino
     */
    @Override
    public void doFilter(ServletRequest peticion, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {
        peticion.setCharacterEncoding("UTF-8");
        respuesta.setCharacterEncoding("UTF-8");
        cadena.doFilter(peticion, respuesta);
    }
}
