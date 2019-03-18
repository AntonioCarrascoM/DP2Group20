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

<spring:message code="periodRecord.title" var="title" />
<spring:message code="periodRecord.description" var="description" />
<spring:message code="periodRecord.startYear" var="startYear" />
<spring:message code="periodRecord.endYear" var="endYear" />
<spring:message code="periodRecord.photos" var="photos" />
<spring:message code="periodRecord.save" var="save" />
<spring:message code="periodRecord.cancel" var="cancel" />

<security:authorize access="hasRole('BROTHERHOOD')">

	<form:form action="${requestURI}" modelAttribute="periodRecord">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />
		<form:hidden path="brotherhood" />

		<%-- Edition forms --%>



		<acme:textbox code="periodRecord.title" path="title" />
		
		<acme:textarea code="periodRecord.description" path="description" />
		
		<acme:textbox code="periodRecord.startYear" path="startYear" />
		
		<acme:textbox code="periodRecord.endYear" path="endYear" />
		
		<acme:textarea code="periodRecord.photos" path="photos"
			placeholder="inceptionRecord.warning" />



		<%-- Buttons --%>

		<acme:submit name="save" code="periodRecord.save" />

		<acme:cancel url="welcome/index.do" code="periodRecord.cancel" />


	</form:form>
</security:authorize>