<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="titulo" value="Resultado de la búsqueda"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<c:choose>
    <c:when test="${empty empleados}">
        <div class="alerta info">No se ha encontrado ningún empleado con esos criterios.</div>
    </c:when>
    <c:otherwise>
        <p class="subtitulo">
            <c:choose>
                <c:when test="${sinCriterios}">No has indicado ningún criterio: se muestran todos los empleados.</c:when>
                <c:otherwise>Empleados que cumplen los criterios indicados.</c:otherwise>
            </c:choose>
            Encontrados: <strong>${fn:length(empleados)}</strong>
        </p>
        <div class="tabla-contenedor">
            <table class="tabla">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>DNI</th>
                        <th>Sexo</th>
                        <th class="numerico">Categoría</th>
                        <th class="numerico">Años trabajados</th>
                        <th class="numerico">Sueldo</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="e" items="${empleados}">
                        <tr>
                            <td><c:out value="${e.nombre}"/></td>
                            <td><c:out value="${e.dni}"/></td>
                            <td><c:out value="${e.sexo}"/></td>
                            <td class="numerico"><c:out value="${e.categoria}"/></td>
                            <td class="numerico"><c:out value="${e.anyos}"/></td>
                            <td class="numerico">
                                <c:choose>
                                    <c:when test="${empty e.sueldo}">—</c:when>
                                    <c:otherwise><fmt:formatNumber value="${e.sueldo}" type="currency" currencySymbol="€"/></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:url var="enlaceModificar" value="/modificar">
                                    <c:param name="dni" value="${e.dni}"/>
                                </c:url>
                                <a class="boton pequeno" href="${enlaceModificar}">Modificar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
