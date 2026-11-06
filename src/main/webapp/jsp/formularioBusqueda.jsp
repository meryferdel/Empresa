<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="titulo" value="Buscar empleados para modificar"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<p class="subtitulo">Rellena uno o varios campos. Si rellenas varios, se buscan los empleados que cumplan
    todos a la vez. El DNI y el nombre buscan texto contenido; el resto, el valor exacto.
    Si lo dejas todo vacío, se muestran todos los empleados.</p>

<c:if test="${not empty errores}">
    <div class="alerta error">
        <strong>Revisa los datos:</strong>
        <ul>
            <c:forEach var="mensaje" items="${errores}">
                <li><c:out value="${mensaje}"/></li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<form class="formulario rejilla" action="${ctx}/buscar" method="get">
    <div class="campo">
        <label for="dni">DNI</label>
        <input type="text" id="dni" name="dni" maxlength="9" value="<c:out value='${param.dni}'/>">
    </div>
    <div class="campo">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" maxlength="100" value="<c:out value='${param.nombre}'/>">
    </div>
    <div class="campo">
        <label for="sexo">Sexo</label>
        <select id="sexo" name="sexo">
            <option value="">Cualquiera</option>
            <option value="M" ${param.sexo == 'M' ? 'selected' : ''}>M</option>
            <option value="F" ${param.sexo == 'F' ? 'selected' : ''}>F</option>
        </select>
    </div>
    <div class="campo">
        <label for="categoria">Categoría (1-10)</label>
        <input type="number" id="categoria" name="categoria" min="1" max="10" value="<c:out value='${param.categoria}'/>">
    </div>
    <div class="campo">
        <label for="anyos">Años trabajados</label>
        <input type="number" id="anyos" name="anyos" min="0" value="<c:out value='${param.anyos}'/>">
    </div>
    <div class="campo">
        <label for="sueldo">Sueldo</label>
        <input type="number" id="sueldo" name="sueldo" min="0" value="<c:out value='${param.sueldo}'/>">
    </div>
    <div class="campo ancho-completo">
        <button type="submit" class="boton">Buscar</button>
    </div>
</form>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
