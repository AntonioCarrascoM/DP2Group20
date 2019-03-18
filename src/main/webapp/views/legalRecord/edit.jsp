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

<spring:message code="legalRecord.title" var="title" />
<spring:message code="legalRecord.description" var="description" />
<spring:message code="legalRecord.legalName" var="legalName" />
<spring:message code="legalRecord.vatNumber" var="vatNumber" />
<spring:message code="legalRecord.applicableLaws" var="applicableLaws" />
<spring:message code="legalRecord.save" var="save" />
<spring:message code="legalRecord.cancel" var="cancel" />

<security:authorize access="hasRole('BROTHERHOOD')">

	<form:form action="${requestURI}" modelAttribute="legalRecord">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />
		<form:hidden path="brotherhood" />

		<%-- Edition forms --%>



		<acme:textbox code="legalRecord.title" path="title" />
		
		<acme:textarea code="legalRecord.description" path="description" />
		
		<acme:textbox code="legalRecord.legalName" path="legalName" />	
			
		<acme:textarea code="legalRecord.applicableLaws" path="applicableLaws" />


		<%-- Buttons --%>

		<acme:submit name="save" code="legalRecord.save" />

		<acme:cancel url="welcome/index.do" code="legalRecord.cancel" />


	</form:form>
</security:authorize>