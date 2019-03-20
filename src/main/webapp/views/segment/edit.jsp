<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<%-- Stored message variables --%>

<spring:message code="segment.originCoordinatesX" var="originCoordinatesX" />
<spring:message code="segment.originCoordinatesY" var="originCoordinatesY" />
<spring:message code="segment.destinationCoordinatesX" var="destinationCoordinatesX" />
<spring:message code="segment.destinationCoordinatesY" var="destinationCoordinatesY" />
<spring:message code="segment.originDate" var="originDate" />
<spring:message code="segment.destinationDate" var="destinationDate" />
<spring:message code="segment.parade" var="parade" />
<spring:message code="segment.save" var="save" />
<spring:message code="segment.cancel" var="cancel" />
<spring:message code="segment.formatDate" var="formatDate" />


<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="${requestURI}" modelAttribute="segment">

	<%-- Form fields --%>

	<form:hidden path="id" />
	<form:hidden path="parade" />
	
	<jstl:if test="${empty orderedSegments}">
	<acme:textbox code="segment.originCoordinatesX"  path="originCoordX"/> <br />
	<acme:textbox code="segment.originCoordinatesY"  path="originCoordY"/> <br />
	</jstl:if>
	<acme:textbox code="segment.destinationCoordinatesX"  path="destinationCoordX" /> <br />
	<acme:textbox code="segment.destinationCoordinatesY"  path="destinationCoordY"/> <br />
	<jstl:if test="${empty orderedSegments}">
	<acme:textbox code="segment.originDate"  path="originDate" placeholder="segment.date.placeholder"/> <br />
	</jstl:if>
	<acme:textbox code="segment.destinationDate"  path="destinationDate" placeholder="segment.date.placeholder"/> <br />
		 
	<%-- Buttons --%>
	
	<acme:submit code="segment.save" name="save"/>
	<acme:cancel code="segment.cancel" url ="parade/brotherhood/list.do" />
			
</form:form>

</security:authorize>

