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

<spring:message code="position.createFloat" var="createFloat" />


<%-- Customer list view --%>



<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="nameES" title="${nameES}" sortable="true" />
	
	<display:column property="nameEN" title="${nameEN}" sortable="true" />
	
	
		
	<security:authorize access="hasRole('ADMIN')">
	<%-- Display --%>
		<spring:url var="displayUrl" value="position/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
	
	</security:authorize>
	

</display:table>
