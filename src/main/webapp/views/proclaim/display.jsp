<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>



<spring:message code="proclaim.description" var="description" />
<spring:message code="proclaim.publicationMoment" var="publicationMoment" />
<spring:message code="proclaim.formatDate" var="formatDate" />



<%-- For the selected proclaim, display the following information: --%>

	
	<jstl:out value="${description}" />:
	<jstl:out value="${proclaim.description}"/>
	<br />

	
	<jstl:out value="${publicationMoment}" />:
	<fmt:formatDate value="${proclaim.publicationMoment}" pattern="${formatDate}"/>
	<br />

