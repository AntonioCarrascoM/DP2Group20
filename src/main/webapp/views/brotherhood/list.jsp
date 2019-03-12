<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="brotherhood.title" var="msgTitle" />
<spring:message code="brotherhood.name"  var="msgName"/>
<spring:message code="brotherhood.surname" var="msgSurname" />
<spring:message code="brotherhood.display" var="msgDisplay" />
<spring:message code="brotherhood.members" var="msgMembers" />
<spring:message code="brotherhood.floats" var="msgFloats" />
<spring:message code="brotherhood.parades" var="msgParades"/>



<display:table pagesize="5" class="displaytag" name="brotherhoods" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="title" title="${msgTitle}" sortable="true" />

	<display:column property="name" title="${msgName}" sortable="true" />



	<spring:url var="displayUrl" value="brotherhood/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgDisplay}">
		<a href="${displayUrl}"><jstl:out value="${msgDisplay}" /></a>
	</display:column>

	<spring:url var="paradesUrl" value="parade/listByBrotherhood.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgParades}">
		<a href="${paradesUrl}"><jstl:out value="${msgParades}" /></a>
	</display:column>

	<spring:url var="floatUrl" value="float/listByBrotherhood.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgFloats}">
		<a href="${floatUrl}"><jstl:out value="${msgFloats}" /></a>
	</display:column>

	<spring:url var="membersUrl" value="member/listByBrotherhood.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgMembers}">
		<a href="${membersUrl}"><jstl:out value="${msgMembers}" /></a>
	</display:column>


</display:table>
