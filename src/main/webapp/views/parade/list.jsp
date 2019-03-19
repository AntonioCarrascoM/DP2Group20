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

<spring:message code="parade.edit" var="edit" />
<spring:message code="parade.save" var="save" />
<spring:message code="parade.cancel" var="cancel" />
<spring:message code="parade.ticker" var="ticker" />
<spring:message code="parade.title" var="title" />
<spring:message code="parade.display" var="display" />
<spring:message code="parade.delete" var="msgDelete" />
<spring:message code="parade.delete.confirm" var="msgConfirm" />
<spring:message code="parade.moment" var="moment" />
<spring:message code="parade.formatDate" var="formatDate" />
<spring:message code="parade.create" var="msgCreate" />
<spring:message code="parade.request" var="msgCreateRequest" />
<spring:message code="parade.requestList" var="msgListRequest" />
<spring:message code="parade.area.empty" var="msgAreaEmpty" />
<spring:message code="parade.rejectionReason" var="rejectionReasonMsg" />
<spring:message code="parade.paradeStatus" var="paradeStatusMsg" />



<%-- Parade list view --%>

<display:table pagesize="5" class="displaytag" name="parades"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="title" title="${title}" sortable="true" />
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	
	<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>	
	<security:authorize access="hasRole('CHAPTER')">
		<display:column property="paradeStatus" title="${paradeStatusMsg}" sortable="true" />
	</security:authorize>
		
	<security:authorize access="hasRole('BROTHERHOOD')">
	<%-- Requests --%>
	<spring:url var="requestsUrl" value="request/brotherhood/list.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgListRequest}">
			<a href="${requestsUrl}"><jstl:out value="${msgListRequest}" /></a>
		</display:column>
	<%-- Display --%>
		<spring:url var="displayUrl" value="parade/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<%-- Edit --%>
		
		<spring:url var="editUrl" value="parade/brotherhood/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${edit}">
	<jstl:if test="${row.finalMode eq false}">
	
		<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	</jstl:if>
		
	</display:column>
	
	<%-- Delete --%>
	
	
	
		<spring:url var="deleteUrl" value="parade/brotherhood/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgDelete}">
		<jstl:if test="${row.finalMode eq false}">
				<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out
						value="${msgDelete}" /></a>
		</jstl:if>
		</display:column>
	
	</security:authorize>
	
	<security:authorize access="hasRole('MEMBER')">
	<spring:url var="requestUrl" value="request/member/create.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${msgCreateRequest}">
			<a href="${requestUrl}"><jstl:out value="${msgCreateRequest}" /></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CHAPTER')">
	<spring:url var="editUrl" value="parade/chapter/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	
	
	<jstl:if test="${row.finalMode eq true and row.paradeStatus.name == 'SUBMITTED'}">
		<display:column title="${edit}">
		<a href="${editUrl}"><jstl:out value="${paradeStatusMsg}" /></a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${row.finalMode eq true and row.paradeStatus.name == 'REJECTED' and empty row.rejectionReason}">
		<display:column title="${edit}">
		<a href="${editUrl}"><jstl:out value="${rejectionReasonMsg}" /></a>
		</display:column>
	</jstl:if>
		
	
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">

<!-- A brotherhood cannot organise any parades until they selected an area -->
	<jstl:if test="${empty brotherhood.area}">
	<br>
	<jstl:out value="${msgAreaEmpty}" />
	</jstl:if>
	<jstl:if test="${not empty brotherhood.area}">
	<spring:url var="createUrl" value="parade/brotherhood/create.do"/>
	<a href="${createUrl}"><jstl:out value="${msgCreate}"/></a>
	</jstl:if>
</security:authorize>	
