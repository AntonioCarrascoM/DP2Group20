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

<spring:message code="chapter.title" var="msgTitle" />
<spring:message code="chapter.name"  var="msgName"/>
<spring:message code="chapter.surname" var="msgSurname" />
<spring:message code="chapter.display" var="msgDisplay" />
<spring:message code="chapter.members" var="msgMembers" />
<spring:message code="chapter.brotherhoods" var="msgBrotherhoods" />
<spring:message code="chapter.proclaims" var="msgProclaims" />
<spring:message code="chapter.area" var="msgArea"/>



<display:table pagesize="5" class="displaytag" name="chapters" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="title" title="${msgTitle}" sortable="true" />

	<spring:url var="displayUrl" value="chapter/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgDisplay}">
		<a href="${displayUrl}"><jstl:out value="${msgDisplay}" /></a>
	</display:column>

	<spring:url var="areaURL" value="area/display.do">
		<spring:param name="varId" value="${row.area.id}" />
	</spring:url>

	<display:column title="${msgArea}">
		<a href="${areaURL}"><jstl:out value="${msgArea}" /></a>
	</display:column>

	<spring:url var="proclaimsUrl" value="proclaim/listByChapter.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgProclaims}">
		<a href="${proclaimsUrl}"><jstl:out value="${msgProclaims}" /></a>
	</display:column>

	<spring:url var="brotherhoodUrl" value="brotherhood/listByChapter.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgBrotherhoods}">
		<a href="${brotherhoodUrl}"><jstl:out value="${msgBrotherhoods}" /></a>
	</display:column>

</display:table>
