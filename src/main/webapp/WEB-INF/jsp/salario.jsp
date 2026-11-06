<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="titulo" value="Salario del empleado"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<c:choose>
    <c:when test="${empty empleado}">
        <div class="alerta error">No existe ningún empleado con el DNI
            <strong><c:out value="${dniBuscado}"/></strong>.</div>
    </c:when>
    <c:when test="${empty empleado.sueldo}">
        <div class="alerta info"><strong><c:out value="${empleado.nombre}"/></strong>
            (<c:out value="${empleado.dni}"/>) existe, pero todavía no tiene ninguna nómina guardada.</div>
    </c:when>
    <c:otherwise>
        <div class="tabla-contenedor">
            <table class="tabla">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>DNI</th>
                        <th class="numerico">Salario</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><c:out value="${empleado.nombre}"/></td>
                        <td><c:out value="${empleado.dni}"/></td>
                        <td class="numerico destacado">
                            <fmt:formatNumber value="${empleado.sueldo}" type="currency" currencySymbol="€"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
