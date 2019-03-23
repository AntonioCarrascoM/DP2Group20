<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
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
<spring:message code="parade.delete.path" var="msgDeletePath" />
<spring:message code="parade.copy" var="msgCopy" />
<spring:message code="parade.create.segment" var="msgCreateSegment" />
<spring:message code="parade.path" var="msgPath" />
<spring:message code="parade.rejectionReason" var="rejectionReasonMsg" />
<spring:message code="parade.paradeStatus" var="paradeStatusMsg" />



<%-- Parade list view --%>

<display:table pagesize="5" class="displaytag" name="parades"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	<jstl:if test="${row.finalMode eq true}">
		<jstl:set var="colorValue" value="default" />
	</jstl:if>
	<jstl:if
		test="${row.finalMode eq true and row.paradeStatus.name == 'SUBMITTED'}">
		<jstl:set var="colorValue" value="grey" />
	</jstl:if>
	<jstl:if
		test="${row.finalMode eq true and row.paradeStatus.name == 'ACCEPTED'}">
		<jstl:set var="colorValue" value="green" />
	</jstl:if>
	<jstl:if
		test="${row.finalMode eq true and row.paradeStatus.name == 'REJECTED'}">
		<jstl:set var="colorValue" value="red" />
	</jstl:if>
	<jstl:if test="${row.finalMode eq false}">
		<jstl:set var="colorValue" value="default" />
	</jstl:if>


	<display:column property="title" title="${title}" sortable="true"
		style="background-color:${colorValue}" />

	<display:column property="ticker" title="${ticker}" sortable="true"
		style="background-color:${colorValue}" />

	<display:column title="${moment}" sortable="true"
		style="background-color:${colorValue}">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>
	<security:authorize access="hasRole('CHAPTER')">
		<display:column property="paradeStatus" title="${paradeStatusMsg}"
			sortable="true" style="background-color:${colorValue}" />
	</security:authorize>

	<security:authorize access="hasRole('BROTHERHOOD')">

		<%-- Requests --%>
		<spring:url var="requestsUrl" value="request/brotherhood/list.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgListRequest}"
			style="background-color:${colorValue}">
			<a href="${requestsUrl}"><jstl:out value="${msgListRequest}" /></a>
		</display:column>

		<%-- Segments --%>
		<spring:url var="segmentsUrl" value="segment/brotherhood/list.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgPath}"
			style="background-color:${colorValue}">
			<a href="${segmentsUrl}"><jstl:out value="${msgPath}" /></a>
		</display:column>
		<spring:url var="createSegmentUrl"
			value="segment/brotherhood/create.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgCreateSegment}"
			style="background-color:${colorValue}">
			<jstl:if test="${row.finalMode eq false}">
				<a href="${createSegmentUrl}"><jstl:out
						value="${msgCreateSegment}" /></a>
			</jstl:if>
		</display:column>

		<%-- Display --%>
		<spring:url var="displayUrl" value="parade/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}"
			style="background-color:${colorValue}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>

		<%-- Edit --%>

		<spring:url var="editUrl" value="parade/brotherhood/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${edit}" style="background-color:${colorValue}">
			<jstl:if test="${row.finalMode eq false}">
				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
			</jstl:if>
		</display:column>

		<%-- Delete --%>

		<spring:url var="deleteUrl" value="parade/brotherhood/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgDelete}"
			style="background-color:${colorValue}">
			<jstl:if test="${row.finalMode eq false}">
				<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out
						value="${msgDelete}" /></a>
			</jstl:if>
		</display:column>

		<%-- Delete path--%>

		<spring:url var="deletePathUrl" value="segment/brotherhood/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgDeletePath}"
			style="background-color:${colorValue}">
			<jstl:if test="${row.finalMode eq false}">
				<a href="${deletePathUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out
						value="${msgDeletePath}" /></a>
			</jstl:if>
		</display:column>

		<%-- Copy--%>

		<spring:url var="copyUrl" value="parade/brotherhood/copy.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgCopy}"
			style="background-color:${colorValue}">
			<a href="${copyUrl}" onclick="return confirm('${msgConfirm}')"> <jstl:out
					value="${msgCopy}" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('MEMBER')">
		<spring:url var="requestUrl" value="request/member/create.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${msgCreateRequest}"
			style="background-color:${colorValue}">
			<a href="${requestUrl}"><jstl:out value="${msgCreateRequest}" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CHAPTER')">
		<spring:url var="editUrl" value="parade/chapter/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column property="rejectionReason"
			title="${rejectionReasonMsg}" sortable="true"
			style="background-color:${colorValue}" />

		<display:column title="${edit}" style="background-color:${colorValue}">
			<jstl:if
				test="${row.finalMode eq true and row.paradeStatus.name == 'SUBMITTED'}">
				<a href="${editUrl}"><jstl:out value="${paradeStatusMsg}" /></a>
			</jstl:if>

			<jstl:if
				test="${row.finalMode eq true and row.paradeStatus.name == 'REJECTED' and empty row.rejectionReason}">
				<a href="${editUrl}"><jstl:out value="${rejectionReasonMsg}" /></a>
			</jstl:if>
		</display:column>

	</security:authorize>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">

	<!-- A brotherhood cannot organise any parades until they selected an area -->
	<jstl:if test="${empty brotherhood.area}">
		<br>
		<jstl:out value="${msgAreaEmpty}" />
	</jstl:if>
	<jstl:if test="${not empty brotherhood.area}">
		<spring:url var="createUrl" value="parade/brotherhood/create.do" />
		<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
	</jstl:if>
</security:authorize>
