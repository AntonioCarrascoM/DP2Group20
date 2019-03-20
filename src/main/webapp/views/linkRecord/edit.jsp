<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="linkRecord.title" var="title" />
<spring:message code="linkRecord.description" var="description" />
<spring:message code="linkRecord.link" var="link" />
<spring:message code="linkRecord.save" var="save" />
<spring:message code="linkRecord.cancel" var="cancel" />

<security:authorize access="hasRole('BROTHERHOOD')">

	<form:form action="${requestURI}" modelAttribute="linkRecord">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />
		<form:hidden path="brotherhood" />

		<%-- Edition forms --%>



		<acme:textbox code="linkRecord.title" path="title" />
		
		<acme:textarea code="linkRecord.description" path="description" />
		
		<acme:textbox code="linkRecord.link" path="link" />	
			


		<%-- Buttons --%>

		<acme:submit name="save" code="linkRecord.save" />

		<acme:cancel url="welcome/index.do" code="linkRecord.cancel" />


	</form:form>
</security:authorize>