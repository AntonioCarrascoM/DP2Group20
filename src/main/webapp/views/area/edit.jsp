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

<spring:message code="area.name" var="name" />
<spring:message code="area.warning" var="warning" />
<spring:message code="area.pictures" var="pictures" />
<spring:message code="area.save" var="save" />
<spring:message code="area.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

	<form:form action="${requestURI}" modelAttribute="area">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />

		<%-- Edition forms --%>



		<acme:textbox code="area.name" path="name" />

		<acme:textarea code="area.pictures" path="pictures"
			placeholder="area.warning" />


		<%-- Buttons --%>

		<acme:submit name="save" code="area.save" />

		<acme:cancel url="welcome/index.do" code="area.cancel" />


	</form:form>
</security:authorize>