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

<spring:message code="periodRecord.title"  var="title"/>
<spring:message code="periodRecord.description" var="description" />
<spring:message code="periodRecord.startYear" var="startYear" />
<spring:message code="periodRecord.endYear" var="endYear" />
<spring:message code="periodRecord.photos" var="photos" />
<spring:message code="periodRecord.edit" var="edit" />
<spring:message code="periodRecord.return" var="return" />


	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${title}" />:
	<jstl:out value="${periodRecord.title}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${periodRecord.description}" />
	<br />
	
	<jstl:out value="${startYear}" />:
	<jstl:out value="${periodRecord.startYear}" />
	<br />
	
	<jstl:out value="${endYear}" />:
	<jstl:out value="${periodRecord.endYear}" />
	<br />
	
	<jstl:out value="${photos}" />:
	<jstl:out value="${periodRecord.photos}" />
	<br />
	
	

<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:url var="editUrl"
		value="periodRecord/brotherhood/edit.do">
		<spring:param name="periodRecordId"
			value="${periodRecord.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
</security:authorize>
<a href="#"><jstl:out value="${return}" /></a>