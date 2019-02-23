<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<spring:message code="procession.ticker" var="ticker" />
<spring:message code="procession.title" var="title" />
<spring:message code="procession.description" var="description" />
<spring:message code="procession.moment" var="moment" />
<spring:message code="procession.finalMode" var="finalMode" />
<spring:message code="procession.floats" var="floats" />
<spring:message code="procession.brotherhood" var="brotherhood" />


<%-- For the selected procession, display the following information: --%>

	<jstl:out value="${ticker}" />:
	<jstl:out value="${procession.ticker}" />
	<br />
	
	<jstl:out value="${title}" />:
	<jstl:out value="${procession.title}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${procession.description}"/>
	<br />
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${procession.moment}" pattern="${formatDate}"/>
	<br />
		
	<jstl:out value="${finalMode}" />:
	<jstl:out value="${procession.finalMode}"/>
	<br />
	
	
		<%-- Mostrar floats --%>
			
	<security:authorize access="hasRole('BROTHERHOOD')">
	
	<jstl:out value="${floats}" />:
		<spring:url var="floatsUrl" value="float/brotherhood/list.do">
			<spring:param name="varId" value="${procession.id}"/>
		</spring:url>
		<a href="${floatsUrl}"><jstl:out value="${floats}" /></a>
	<br />
	

	</security:authorize>
	
	<%-- The brotherhood --%>
	<jstl:out value="${brotherhood}" />:
		
	<jstl:out value="${procession.brotherhood.name} ${procession.brotherhood.middleName} ${procession.brotherhood.surname}" /></a>
	<br />
	
	