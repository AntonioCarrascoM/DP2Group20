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

<spring:message code="member.name" var="msgName" />
<spring:message code="member.middleName"  var="msgMiddleName"/>
<spring:message code="member.surname" var="msgSurname" />
<spring:message code="member.photo" var="msgPhoto" />
<spring:message code="member.email" var="msgEmail" />
<spring:message code="member.phone" var="msgPhone" />
<spring:message code="member.address" var="msgAddress" />
<spring:message code="member.score" var="msgScore" />
<spring:message code="member.enrolments" var="msgEnrolments" />
<spring:message code="member.return" var="msgReturn" />

<%-- Display the following information about the audit record: --%>
	
	<jstl:out value="${msgName}" />:
	<jstl:out value="${member.name}" />
	<br />
	
	<jstl:out value="${msgMiddleName}" />:
	<jstl:out value="${member.middleName}" />
	<br />
	
	<jstl:out value="${msgSurname}" />:
	<jstl:out value="${member.surname}" />
	<br />
	
	<jstl:out value="${msgPhoto}" />:
	<a href="${member.photo}"><jstl:out value="${member.photo}" /></a>
	<br />
	
	<jstl:out value="${msgEmail}" />:
	<jstl:out value="${member.email}" />
	<br />
	
	<jstl:out value="${msgPhone}" />:
	<jstl:out value="${member.phone}" />
	<br />
	
	<jstl:out value="${msgAddress}" />:
	<jstl:out value="${member.address}" />
	<br />
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:out value="${msgScore}" />:
	<jstl:out value="${member.score}" />
	<br />	
	</security:authorize>
	
	<spring:url var="returnURL" value="/welcome/index.do"/>

	<a href="${returnURL}"><jstl:out value="${msgReturn}" /></a>