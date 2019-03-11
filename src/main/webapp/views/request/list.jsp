<%--
 * action-1.jsp
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

<%-- Stored message variables --%>

<spring:message code="request.member" var="apCustomer" />
<spring:message code="request.status" var="status" />
<spring:message code="request.customRow" var="customRow" />
<spring:message code="request.customColumn" var="customColumn" />
<spring:message code="request.reason" var="reason" />
<spring:message code="request.procession" var="procession" />
<spring:message code="request.edit" var="edit" />
<spring:message code="request.position" var="positionMsg" />
<spring:message code="request.changeStatus" var="changeStatusMsg" />
<spring:message code="request.action" var="action" />
<spring:message code="request.delete" var="delete" />
<spring:message code="request.create" var="create" />
<spring:message code="request.display" var="display" />
<spring:message code="request.confirm" var="confirm" />
<spring:message code="request.cancel" var="cancel" />


<jstl:set var="Brotherhood" value="brotherhood" />
<jstl:set var="Member" value="member" />

<%-- Member list view --%>

<security:authorize access="hasRole('MEMBER')"> 

<display:table pagesize="5" class="displaytag" name="requests"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
		
	<display:column property="procession.ticker" title="${procession}" sortable="true" />
	<display:column property="customRow" title="${customRow}" sortable="true" />
	<display:column property="customColumn" title="${customColumn}" sortable="true" />
	
	<jstl:if test="${row.status.name == 'PENDING'}">
		<jstl:set var="colorValue" value="grey"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'APPROVED'}">
		<jstl:set var="colorValue" value="green"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'REJECTED'}">
		<jstl:set var="colorValue" value="orange"/>
	</jstl:if>
	
	<display:column property="status.name" title="${status}" sortable="true" style="background-color:${colorValue}"/>


	<%-- Edition button --%>

	
	<spring:url var="deleteUrl" value="request/member/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${delete}">
	<jstl:if test="${row.status.name == 'PENDING'}">
		<a href="${deleteUrl}" onclick="return confirm('${confirm}')"><jstl:out value="${delete}" /></a>
		</jstl:if>
	</display:column> 
		
	<spring:url var="displayUrl" value="request/member/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column> 
	
</display:table>

<spring:url var="createUrl" value="request/member/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>

</security:authorize>

<%-- Customer list view --%>

<security:authorize access="hasRole('BROTHERHOOD')"> 

<display:table pagesize="5" class="displaytag" name="requests"
	requestURI="request/brotherhood/list.do" id="row">

	<%-- Attributes --%>
	
	<display:column property="procession.ticker" title="${procession}" />
	
	<jstl:if test="${row.status.name == 'PENDING'}">
		<jstl:set var="colorValue" value="grey"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'APPROVED'}">
		<jstl:set var="colorValue" value="green"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'REJECTED'}">
		<jstl:set var="colorValue" value="orange"/>
	</jstl:if>
	
	<display:column property="status.name" title="${status}" sortable="true" style="background-color:${colorValue}"/>

	<spring:url var="requestDisplayUrl"
		value="request/brotherhood/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${display}">
	<a href="${requestDisplayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
	<%-- Buttons --%>
	
	
	<spring:url var="editUrl" value="request/brotherhood/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		
	<display:column title="${action}">
		<jstl:if test="${row.status == 'APPROVED'}" >
			<a href="${editUrl}"><jstl:out value="${positionMsg}" /></a>
		</jstl:if>
		<jstl:if test="${row.status == 'PENDING'}" >
			<a href="${editUrl}"><jstl:out value="${changeStatusMsg}" /></a>
		</jstl:if>
		</display:column>
		
</display:table>


</security:authorize>