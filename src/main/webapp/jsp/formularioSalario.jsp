<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="titulo" value="Salario de un empleado"/>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<p class="subtitulo">Escribe el DNI del empleado cuyo salario quieres consultar.</p>

<c:if test="${not empty error}">
    <div class="alerta error"><c:out value="${error}"/></div>
</c:if>

<form class="formulario" action="${ctx}/salario" method="get">
    <div class="campo">
        <label for="dni">DNI del empleado</label>
        <input type="text" id="dni" name="dni" maxlength="9" required autofocus
               placeholder="Ejemplo: 32000032G" value="<c:out value='${param.dni}'/>">
    </div>
    <button type="submit" class="boton">Mostrar salario</button>
</form>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
