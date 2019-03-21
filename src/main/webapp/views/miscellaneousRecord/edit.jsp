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

<spring:message code="miscellaneousRecord.title" var="title" />
<spring:message code="miscellaneousRecord.description" var="description" />
<spring:message code="miscellaneousRecord.save" var="save" />
<spring:message code="miscellaneousRecord.cancel" var="cancel" />
<spring:message code="miscellaneousRecord.delete" var="msgDel" />
<spring:message code="miscellaneousRecord.delete.confirm" var="msgConf" />

<security:authorize access="hasRole('BROTHERHOOD')">

	<form:form action="${requestURI}" modelAttribute="miscellaneousRecord">

		<%-- Hidden attributes --%>

		<form:hidden path="id" />

		<%-- Edition forms --%>



		<acme:textbox code="miscellaneousRecord.title" path="title" />
		
		<acme:textarea code="miscellaneousRecord.description" path="description" />
		




		<%-- Buttons --%>

		<acme:submit name="save" code="miscellaneousRecord.save" />
		
		<jstl:if test="${miscellaneousRecord.id != 0}">
			<input type="submit" name="delete" value="${msgDel}"
				onclick="return confirm('${msgConf}')">
		</jstl:if>

		<acme:cancel url="welcome/index.do" code="miscellaneousRecord.cancel" />


	</form:form>
</security:authorize>