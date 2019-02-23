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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="procession.edit" var="edit" />
<spring:message code="procession.save" var="save" />
<spring:message code="procession.cancel" var="cancel" />
<spring:message code="procession.ticker" var="ticker" />
<spring:message code="procession.title" var="title" />
<spring:message code="procession.display" var="display" />
<spring:message code="procession.delete" var="msgDelete" />
<spring:message code="procession.delete.confirm" var="msgConfirm" />
<spring:message code="procession.moment" var="moment" />
<spring:message code="procession.formatDate" var="formatDate" />
<spring:message code="procession.create" var="msgCreate" />
<spring:message code="procession.request" var="msgCreateRequest" />




<%-- Procession list view --%>

<display:table pagesize="5" class="displaytag" name="processions"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="title" title="${title}" sortable="true" />
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	
	<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>	
	
		
	<security:authorize access="hasRole('BROTHERHOOD')">
	<%-- Display --%>
		<spring:url var="displayUrl" value="procession/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<jstl:if test="${procession.finalMode eq false}">
	<%-- Edit --%>
		
		<spring:url var="editUrl" value="procession/brotherhood/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${edit}">
		<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	</display:column>
	
	<%-- Delete --%>
	
		<spring:url var="deleteUrl" value="procession/brotherhood/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgDelete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDelete}" /></a>
		</display:column>
	</jstl:if>
	
		
	
	
	
	<security:authorize access="hasRole('MEMBER')">
	<spring:url var="requestUrl" value="request/member/create.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${msgCreateRequest}">
			<a href="${requestUrl}"><jstl:out value="${msgCreateRequest}" /></a>
	</display:column>
	</security:authorize>
	
	
	</security:authorize>
	

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">	
	<spring:url var="createUrl" value="procession/brotherhood/create.do"/>
	<a href="${createUrl}"><jstl:out value="${msgCreate}"/></a>
</security:authorize>	
