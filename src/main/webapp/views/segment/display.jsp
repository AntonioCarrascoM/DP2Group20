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

<spring:message code="segment.originCoordinates" var="originCoordinates" />
<spring:message code="segment.destinationCoordinates" var="destinationCoordinates" />
<spring:message code="segment.originDate" var="originDate" />
<spring:message code="segment.destinationDate" var="destinationDate" />
<spring:message code="segment.formatDate" var="formatDate" />
<spring:message code="segment.parade" var="parade" />


<security:authorize access="hasRole('BROTHERHOOD')">
<%-- For the selected position, display the following information: --%>

	<jstl:out value="${originCoordinates}" />:
	(<jstl:out value="${segment.originCoordX}" />,
	<jstl:out value="${segment.originCoordY}" />)
	<br />
	
	
	
	<jstl:out value="${destinationCoordinates}" />:
	(<jstl:out value="${segment.destinationCoordX}" />,
	<jstl:out value="${segment.destinationCoordY}" />)
	<br />

	
	<jstl:out value="${originDate}" />:
	<fmt:formatDate value="${segment.originDate}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${destinationDate}" />:
	<fmt:formatDate value="${segment.destinationDate}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${parade}" />:
	<jstl:out value="${segment.parade.ticker}" />
	<br />
	
	</security:authorize>
