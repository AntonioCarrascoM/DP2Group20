<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="message.sent" var="sent" />
<spring:message code="message.priority" var="priority" />
<spring:message code="message.recipient" var="recipient" />
<spring:message code="message.sender" var="sender" />
<spring:message code="message.subject" var="subject" />
<spring:message code="message.display" var="display" />
<spring:message code="message.move" var="move" />
<spring:message code="message.confirm.delete" var="confirm" />
<spring:message code="message.delete" var="delete" />
<spring:message code="message.return" var="returnMsg" />
<spring:message code="message.broadcast" var="broadcast" />
<spring:message code="message.create" var="createMessage" />
<spring:message code="message.formatDate" var="formatDate" />

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="messages" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	
	<display:column property="priority" title="${priority}" sortable="true" />
	
	<display:column title="${sent}" sortable="true">
			<fmt:formatDate value="${row.sent}" pattern="${formatDate}"/>
	</display:column>
	
	<display:column property="subject" title="${subject}" sortable="true" />

	<display:column property="sender.name" title="${sender}"
		sortable="true" />
		
	<display:column property="recipient.name" title="${recipient}"
		sortable="true" />
	

	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="displayUrl" value="message/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

	<spring:url var="moveUrl" value="message/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${move}">
		<a href="${moveUrl}"><jstl:out value="${move}" /></a>
	</display:column>

	<spring:url var="deleteUrl" value="message/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${delete}">
		<a href="${deleteUrl}" onclick="return confirm('${confirm}')"><jstl:out value="${delete}" /></a>
	</display:column>

</display:table>

<spring:url var="createMessageUrl" value="message/create.do" />
<a href="${createMessageUrl}"><jstl:out value="${createMessage}" /></a>

<security:authorize access="hasRole('ADMIN')">
	<spring:url var="broadcastUrl" value="message/administrator/create.do" />
	<a href="${broadcastUrl}"><jstl:out value="${broadcast}" /></a>
</security:authorize>

<spring:url var="returnUrl" value="box/list.do" />
<a href="${returnUrl}"><jstl:out value="${returnMsg}" /></a>