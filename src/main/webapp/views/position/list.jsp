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

<spring:message code="position.edit" var="edit" />
<spring:message code="position.save" var="save" />
<spring:message code="position.cancel" var="cancel" />
<spring:message code="position.nameES" var="nameES" />
<spring:message code="position.nameEN" var="nameEN" />
<spring:message code="position.display" var="display" />
<spring:message code="position.delete" var="delete" />
<spring:message code="position.confirm" var="msgConfirm" />
<spring:message code="position.create" var="createPosition" />


<%-- Administrator list view --%>

<security:authorize access="hasRole('ADMIN')">

<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="nameES" title="${nameES}" sortable="true" />
	
	<display:column property="nameEN" title="${nameEN}" sortable="true" />
	
	<%-- Display --%>
		<spring:url var="displayUrl" value="position/administrator/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
		<%-- Edit --%>
		<spring:url var="editUrl" value="position/administrator/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<%-- Delete --%>
		<spring:url var="deleteUrl" value="position/administrator/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
		</display:column>
	

</display:table>

<spring:url var="createPositionUrl" value="position/administrator/create.do" />
	<a href="${createPositionUrl}"><jstl:out value="${createPosition}" /></a>
	
	</security:authorize>
