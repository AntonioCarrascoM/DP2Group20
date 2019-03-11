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

<spring:message code="position.nameES" var="nameES" />
<spring:message code="position.nameEN" var="nameEN" />
<spring:message code="position.enrolments" var="msgEnrolments" />



<security:authorize access="hasRole('ADMIN')">
<%-- For the selected position, display the following information: --%>

	<jstl:out value="${nameES}" />:
	<jstl:out value="${position.nameES}" />
	<br />
	
	<jstl:out value="${nameEN}" />:
	<jstl:out value="${position.nameEN}"/>
	<br />
	
	
		
			
	
	
	<%-- Mostrar enrolments --%>
		
		<spring:url var="enrolmentsUrl"
		value="enrolment/list.do">
		<spring:param name="varId"
			value="${position.id}"/>
		</spring:url>
	
		<a href="${enrolmentsUrl}"><jstl:out value="${msgEnrolments}" /></a>
	<br />
	
	</security:authorize>
	
	
	
