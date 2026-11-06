<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- Página de error. La usan los servlets (con tituloError y mensajeError) y el servidor
     para las direcciones inexistentes (404) y los fallos inesperados (500). --%>
<c:set var="codigo" value="${requestScope['jakarta.servlet.error.status_code']}"/>
<c:choose>
    <c:when test="${not empty tituloError}">
        <c:set var="titulo" value="${tituloError}"/>
        <c:set var="mensaje" value="${mensajeError}"/>
    </c:when>
    <c:when test="${codigo == 404}">
        <c:set var="titulo" value="Página no encontrada"/>
        <c:set var="mensaje" value="La dirección que has escrito no existe en esta aplicación."/>
    </c:when>
    <c:otherwise>
        <c:set var="titulo" value="Se ha producido un error"/>
        <c:set var="mensaje" value="Ha ocurrido un error inesperado. Inténtalo de nuevo y, si se repite, avisa al administrador."/>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jsp/fragmentos/cabecera.jspf" %>

<div class="alerta error"><c:out value="${mensaje}"/></div>

<%@ include file="/WEB-INF/jsp/fragmentos/pie.jspf" %>
