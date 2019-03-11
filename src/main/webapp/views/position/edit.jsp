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

<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="position.edit" var="edit" />
<spring:message code="position.save" var="save" />
<spring:message code="position.cancel" var="cancel" />
<spring:message code="position.delete" var="delete" />
<spring:message code="position.confirm" var="confirm" />
<spring:message code="position.nameES" var="nameES" />
<spring:message code="position.nameEN" var="nameEN" />




<security:authorize access="hasRole('ADMIN')">

<form:form action="${requestURI}" modelAttribute="position">

	<%-- Form fields --%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="nameES">
		<jstl:out value="${nameES}" />:
	</form:label>
		<form:textarea path="nameES" />
		<form:errors cssClass="error" path="nameES" />
	<br />
	
	
	<form:label path="nameEN">
		<jstl:out value="${nameEN}" />:
	</form:label>
		<form:textarea path="nameEN" />
		<form:errors cssClass="error" path="nameEN" />
	<br />
	
	
	
	
	
	<%-- Buttons --%>
	<input type="submit" name="save" value="${save}" onclick="return confirm('${confirm}')"/>&nbsp;
		
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</form:form>

</security:authorize>

