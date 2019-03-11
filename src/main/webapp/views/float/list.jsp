<%--
 * action-1.jsp
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

<%-- Stored message variables --%>

<spring:message code="float.edit" var="edit" />
<spring:message code="float.save" var="save" />
<spring:message code="float.cancel" var="cancel" />
<spring:message code="float.title" var="title" />
<spring:message code="float.description" var="description" />
<spring:message code="float.display" var="display" />
<spring:message code="float.delete" var="delete" />
<spring:message code="float.create" var="createFloat" />


<%-- Brotherhood list view --%>

<display:table pagesize="5" class="displaytag" name="floats"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="title" title="${title}" sortable="true" />

	<display:column property="description" title="${description}"
		sortable="true" />


	<%-- Display --%>

	<spring:url var="displayUrl" value="float/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

	<%-- Edit --%>

	<security:authorize access="hasRole('BROTHERHOOD')">
	
	<spring:url var="editUrl" value="float/brotherhood/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${edit}">
		<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	</display:column>
	
	<spring:url var="deleteUrl" value="float/brotherhood/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${delete}">
		<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
	</display:column>
	
	
	
	</security:authorize>

</display:table>
	<security:authorize access="hasRole('BROTHERHOOD')">

<spring:url var="createFloatUrl" value="float/brotherhood/create.do" />
	<a href="${createFloatUrl}"><jstl:out value="${createFloat}" /></a>
	</security:authorize>
	