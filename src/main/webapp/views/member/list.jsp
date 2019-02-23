<%--
 * list.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<spring:message code="member.name" var="msgName" />
<spring:message code="member.middleName"  var="msgMiddleName"/>
<spring:message code="member.surname" var="msgSurname" />
<spring:message code="member.display" var="display" />
<spring:message code="member.edit" var="edit" />
<spring:message code="member.delete" var="delete" />
<spring:message code="member.enrol" var="msgCreateMember" />



<display:table pagesize="5" class="displaytag" name="members" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>

	<display:column property="name" title="${msgName}" sortable="true" />

	<display:column property="middleName" title="${msgMiddleName}" sortable="true" />

	<display:column property="surname" title="${msgSurname}" sortable="true" />
	

	<security:authorize access="hasRole('ADMIN')">
	
	<spring:url var="displayUrl" value="member/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	</security:authorize>
	
</display:table>
