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

<%-- Stored message variables --%>

<spring:message code="miscellaneousRecord.title"  var="title"/>
<spring:message code="miscellaneousRecord.description" var="description" />
<spring:message code="miscellaneousRecord.edit" var="edit" />

	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${title}" />:
	<jstl:out value="${miscellaneousRecord.title}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${miscellaneousRecord.description}" />
	<br />
	
	

<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:url var="editUrl"
		value="miscellaneousRecord/brotherhood/edit.do">
		<spring:param name="varId"
			value="${miscellaneousRecord.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
</security:authorize>
