<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>


<spring:message code="request.status" var="status" />
<spring:message code="request.rowAcme" var="rowAcme" />
<spring:message code="request.columnAcme" var="columnAcme" />
<spring:message code="request.reason" var="reason" />
<spring:message code="request.return" var="msgReturn" />




<%-- For the selected request, display the following information: --%>


	<jstl:out value="${status}" />:
	<jstl:out value="${request.status}"/>
	<br />
	
	
	
	<jstl:out value="${rowAcme}" />:
	<jstl:out value="${request.rowAcme}"/>
	<br />
	
	<jstl:out value="${columnAcme}" />:
	<jstl:out value="${request.columnAcme}"/>
	<br />
	
	<jstl:out value="${reason}" />:
	<jstl:out value="${request.reason}"/>
	<br />
	
	<!-- Member display -->
	<jstl:out value="${member}" />:
	<spring:url var="memberUrl" value="member/display.do">
		<spring:param name="varId" value="${request.member.id}"/>
	</spring:url>
	
	<a href="${memberUrl}"><jstl:out value="${request.member.name} ${request.member.middleName} ${request.member.surname}" /></a>
	<br />
	
	<!-- Procession display -->
	<jstl:out value="${procession}" />:
		<spring:url var="processionUrl" value="procession/display.do">
			<spring:param name="varId" value="${request.procession.id}"/>
		</spring:url>
		<a href="${processionUrl}"><jstl:out value="${request.procession.ticker}" /></a>
	<br />
	
	
	
	
	<!-- Return link -->
	<security:authorize access="hasRole('MEMBER')">
		<spring:url var="returnURL" value="request/handyWorker/list.do"/>
	</security:authorize>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
		<spring:url var="returnURL" value="request/brotherhood/list.do"/>
	</security:authorize>
	
	<security:authorize access="hasAnyRole('MEMBER','BROTHERHOOD')">
	<a href="${returnURL}"><jstl:out value="${msgReturn}" /></a>
	</security:authorize>
	
