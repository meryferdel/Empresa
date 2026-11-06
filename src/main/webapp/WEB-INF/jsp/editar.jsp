<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="titulo" value="Modificar empleado"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<c:if test="${not empty mensajeOk}">
    <div class="alerta ok"><c:out value="${mensajeOk}"/></div>
</c:if>

<c:if test="${not empty errores}">
    <div class="alerta error">
        <strong>No se han guardado los cambios. Revisa los datos:</strong>
        <ul>
            <c:forEach var="mensaje" items="${errores}">
                <li><c:out value="${mensaje}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form class="formulario rejilla" action="${ctx}/modificar" method="post">
    <input type="hidden" name="dni" value="<c:out value='${empleado.dni}'/>">

    <div class="campo">
        <label for="dniVisible">DNI (no se puede cambiar)</label>
        <input type="text" id="dniVisible" value="<c:out value='${empleado.dni}'/>" readonly>
    </div>
    <div class="campo">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" maxlength="100" required
               value="<c:out value='${empleado.nombre}'/>">
    </div>
    <div class="campo">
        <label for="sexo">Sexo</label>
        <select id="sexo" name="sexo" required>
            <option value="M" ${empleado.sexo == 'M' ? 'selected' : ''}>M</option>
            <option value="F" ${empleado.sexo == 'F' ? 'selected' : ''}>F</option>
        </select>
    </div>
    <div class="campo">
        <label for="categoria">Categoría</label>
        <select id="categoria" name="categoria" required>
            <c:forEach var="n" begin="1" end="10">
                <option value="${n}" ${empleado.categoria == n ? 'selected' : ''}>${n}</option>
            </c:forEach>
        </select>
    </div>
    <div class="campo">
        <label for="anyos">Años trabajados</label>
        <input type="number" id="anyos" name="anyos" min="0" required
               value="<c:out value='${empleado.anyos}'/>">
    </div>
    <div class="campo">
        <label for="sueldoVisible">Sueldo (calculado, no se puede editar)</label>
        <c:choose>
            <c:when test="${empty empleado.sueldo}">
                <c:set var="sueldoTexto" value="Sin nómina"/>
            </c:when>
            <c:otherwise>
                <fmt:formatNumber var="sueldoTexto" value="${empleado.sueldo}" type="currency" currencySymbol="€"/>
            </c:otherwise>
        </c:choose>
        <input type="text" id="sueldoVisible" readonly value="<c:out value='${sueldoTexto}'/>">
    </div>
    <div class="campo ancho-completo">
        <p class="nota">Al guardar, el sueldo se recalcula automáticamente con la categoría y los años trabajados.</p>
        <button type="submit" class="boton">Guardar cambios</button>
    </div>
</form>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
