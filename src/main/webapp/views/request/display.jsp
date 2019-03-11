<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<spring:message code="request.customRow" var="customRow" />
<spring:message code="request.customColumn" var="customColumn" />
<spring:message code="request.reason" var="reason" />
<spring:message code="request.member" var="member" />
<spring:message code="request.procession" var="procession" />





<%-- For the selected request, display the following information: --%>


	<jstl:out value="${status}" />:
	<jstl:out value="${request.status}"/>
	<br />
	
	<jstl:if test="${request.status == 'REJECTED'}" >
	<jstl:out value="${reason}" />:
	<jstl:out value="${request.reason}"/>
	<br />
	</jstl:if>
	
	
	
	<jstl:if test="${request.status == 'APPROVED'}" >
	<jstl:out value="${customRow}" />:
	<jstl:out value="${request.customRow}"/>
	<br />
	
	<jstl:out value="${customColumn}" />:
	<jstl:out value="${request.customColumn}"/>
	<br />
	</jstl:if>
	
	
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
	

	
	
	

