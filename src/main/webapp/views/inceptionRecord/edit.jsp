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

<spring:message code="inceptionRecord.title" var="name" />
<spring:message code="inceptionRecord.warning" var="warning" />
<spring:message code="inceptionRecord.description" var="description" />
<spring:message code="inceptionRecord.photos" var="photos" />
<spring:message code="inceptionRecord.save" var="save" />
<spring:message code="inceptionRecord.cancel" var="cancel" />
<spring:message code="inceptionRecord.delete" var="msgDel" />
<spring:message code="inceptionRecord.delete.confirm" var="msgConf" />

<security:authorize access="hasRole('BROTHERHOOD')">

	<form:form action="${requestURI}" modelAttribute="inceptionRecord">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />

		<%-- Edition forms --%>



		<acme:textbox code="inceptionRecord.title" path="title" />
		
		<acme:textarea code="inceptionRecord.description" path="description" />
		
		<acme:textarea code="inceptionRecord.photos" path="photos"
			placeholder="inceptionRecord.warning" />


		<%-- Buttons --%>

		<acme:submit name="save" code="inceptionRecord.save" />
		
		<jstl:if test="${inceptionRecord.id != 0}">
			<input type="submit" name="delete" value="${msgDel}"
				onclick="return confirm('${msgConf}')">
		</jstl:if>

		<acme:cancel url="welcome/index.do" code="inceptionRecord.cancel" />


	</form:form>
</security:authorize>