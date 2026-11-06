<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="titulo" value="Listado de empleados"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<c:choose>
    <c:when test="${empty empleados}">
        <div class="alerta info">No hay ningún empleado en la base de datos.</div>
    </c:when>
    <c:otherwise>
        <p class="subtitulo">Empleados registrados: <strong>${fn:length(empleados)}</strong></p>
        <div class="tabla-contenedor">
            <table class="tabla">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>DNI</th>
                        <th>Sexo</th>
                        <th class="numerico">Categoría</th>
                        <th class="numerico">Años trabajados</th>
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
