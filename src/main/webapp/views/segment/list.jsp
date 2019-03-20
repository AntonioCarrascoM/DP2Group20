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

<spring:message code="segment.edit" var="edit" />
<spring:message code="segment.save" var="save" />
<spring:message code="segment.cancel" var="cancel" />
<spring:message code="segment.originCoordinatesX" var="originCoordinatesX" />
<spring:message code="segment.originCoordinatesY" var="originCoordinatesY" />
<spring:message code="segment.destinationCoordinatesX" var="destinationCoordinatesX" />
<spring:message code="segment.destinationCoordinatesY" var="destinationCoordinatesY" />
<spring:message code="segment.originDate" var="originDate" />
<spring:message code="segment.destinationDate" var="destinationDate" />
<spring:message code="segment.parade" var="parade" />
<spring:message code="segment.save" var="save" />
<spring:message code="segment.cancel" var="cancel" />
<spring:message code="segment.display" var="display" />
<spring:message code="segment.delete" var="delete" />
<spring:message code="segment.confirm" var="msgConfirm" />
<spring:message code="segment.create" var="createSegment" />

<security:authorize access="hasRole('BROTHERHOOD')">

<display:table pagesize="5" class="displaytag" name="segments"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="originCoordX" title="${originCoordinatesX}" sortable="true" />
	<display:column property="originCoordY" title="${originCoordinatesY}" sortable="true" />
	<display:column property="destinationCoordX" title="${destinationCoordinatesX}" sortable="true" />
	<display:column property="destinationCoordY" title="${destinationCoordinatesY}" sortable="true" />
	<display:column property="destinationDate" title="${destinationDate}" sortable="true" />


		<%-- Display --%>
		
		<spring:url var="displayUrl" value="segment/brotherhood/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
		<%-- Edit --%>

		<spring:url var="editUrl" value="segment/brotherhood/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${edit}">
		<jstl:if test="${row.parade.finalMode eq false}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
			</jstl:if>
		</display:column>
	
</display:table>
	</security:authorize>
