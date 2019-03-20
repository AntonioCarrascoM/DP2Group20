<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>
<spring:message code="chapter.name" var="msgName" />
<spring:message code="chapter.middleName"  var="msgMiddleName"/>
<spring:message code="chapter.surname" var="msgSurname" />
<spring:message code="chapter.photo" var="msgPhoto" />
<spring:message code="chapter.email" var="msgEmail" />
<spring:message code="chapter.phone" var="msgPhone" />
<spring:message code="chapter.address" var="msgAddress" />
<spring:message code="chapter.score" var="msgScore" />
<spring:message code="chapter.title" var="msgTitle" />
<spring:message code="chapter.area" var="msgArea" />
<spring:message code="chapter.parades" var="msgParades" />
<spring:message code="chapter.members" var="msgMembers" />
<spring:message code="chapter.floats" var="msgFloats" />
<spring:message code="chapter.return" var="msgReturn" />


<%-- Display the following information about the audit record: --%>
	
	<jstl:out value="${msgName}" />:
	<jstl:out value="${chapter.name}" />
	<br /> 
	
	<jstl:out value="${msgMiddleName}" />:
	<jstl:out value="${chapter.middleName}" />
	<br />
	
	<jstl:out value="${msgSurname}" />:
	<jstl:out value="${chapter.surname}" />
	<br />
	
	<jstl:out value="${msgPhoto}" />:
	<a href="${chapter.photo}"><jstl:out value="${chapter.photo}" /></a>
	<br />
	
	<jstl:out value="${msgEmail}" />:
	<jstl:out value="${chapter.email}" />
	<br />
	
	<jstl:out value="${msgPhone}" />:
	<jstl:out value="${chapter.phone}" />
	<br />
	
	<jstl:out value="${msgAddress}" />:
	<jstl:out value="${chapter.address}" />
	<br />
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:out value="${msgScore}" />:
	<jstl:out value="${chapter.score}" />
	<br />
	</security:authorize>
	
	<jstl:out value="${msgArea}" />:
	<jstl:out value="${chapter.area.name}" />
	<br />
