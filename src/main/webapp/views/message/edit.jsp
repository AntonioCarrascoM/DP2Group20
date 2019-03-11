<%--
 * create.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="message.box.name" var="name" />
<spring:message code="message.move.here" var="move" />

<security:authorize access="isAuthenticated()">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="boxes" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="moveUrl" value="message/move.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${move}">
		<a href="${moveUrl}"><jstl:out value="${move}" /></a>
	</display:column>
	
</display:table>

</security:authorize>