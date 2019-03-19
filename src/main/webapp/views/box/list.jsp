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

<%-- Stored message variables --%>

<spring:message code="box.name" var="name" />
<spring:message code="box.parent" var="parent" />
<spring:message code="box.children" var="children" />
<spring:message code="box.messages" var="messages" />
<spring:message code="box.create" var="create" />
<spring:message code="box.delete" var="delete" />
<spring:message code="box.edit" var="edit" />
<spring:message code="box.confirm.delete" var="confirm" />

<security:authorize
	access="isAuthenticated()">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="boxes" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" sortable="true" />

		<display:column property="parent.name" title="${parent}"
			sortable="true" />

		<%-- Links towards display, apply, edit and cancel views --%>

		<spring:url var="messageUrl" value="message/list.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${messages}">
			<a href="${messageUrl}"><jstl:out value="${messages}" /></a>
		</display:column>

		<spring:url var="childrenListUrl" value="box/childrenList.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${children}">
			<a href="${childrenListUrl}"><jstl:out value="${children}" /></a>
		</display:column>

		<display:column title="${edit}">
			<jstl:if test="${!row.system}">
				<spring:url var="editUrl" value="box/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>


				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
			</jstl:if>
		</display:column>

		<display:column title="${delete}">
			<jstl:if test="${!row.system}">
				<spring:url var="deleteUrl" value="box/delete.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>


				<a href="${deleteUrl}" onclick="return confirm('${confirm}')"><jstl:out
						value="${delete}" /></a>
			</jstl:if>
		</display:column>

	</display:table>

	<spring:url var="createUrl" value="box/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>

</security:authorize>