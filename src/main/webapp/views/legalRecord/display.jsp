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

<spring:message code="legalRecord.title"  var="title"/>
<spring:message code="legalRecord.description" var="description" />
<spring:message code="legalRecord.legalName" var="legalName" />
<spring:message code="legalRecord.vatNumber" var="vatNumber" />
<spring:message code="legalRecord.applicableLaws" var="applicableLaws" />
<spring:message code="legalRecord.edit" var="edit" />


	
	<jstl:out value="${title}" />:
	<jstl:out value="${legalRecord.title}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${legalRecord.description}" />
	<br />
	
	<jstl:out value="${legalName}" />:
	<jstl:out value="${legalRecord.legalName}" />
	<br />
	
	<jstl:out value="${vatNumber}" />:
	<jstl:out value="${legalRecord.vatNumber}" />
	<br />
	
	<jstl:out value="${applicableLaws}" />:
	<jstl:out value="${legalRecord.applicableLaws}" />
	<br />
	
	
	

<security:authorize access="hasRole('BROTHERHOOD')">
	<spring:url var="editUrl"
		value="legalRecord/brotherhood/edit.do">
		<spring:param name="varId"
			value="${legalRecord.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
</security:authorize>
