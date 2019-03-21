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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>


<spring:message code="miscellaneousRecord.title" var="name" />
<spring:message code="miscellaneousRecord.description" var="description" />
<spring:message code="miscellaneousRecord.edit" var="edit" />
<spring:message code="miscellaneousRecord.display" var="display" />
<spring:message code="miscellaneousRecord.delete.confirm"
	var="msgConfirm" />
<spring:message code="miscellaneousRecord.delete" var="delete" />
<spring:message code="miscellaneousRecord.create" var="create" />
<spring:message code="miscellaneousRecord.cancel" var="cancel" />




<display:table pagesize="5" class="displaytag"
	name="miscellaneousRecords" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>



	<display:column property="title" title="${name}" sortable="true" />

	<display:column property="description" title="${description}"
		sortable="true" />





	<%-- Edition, Delete & Display button --%>

	<spring:url var="displayUrl"
		value="miscellaneousRecord/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

	<security:authorize access="hasRole('BROTHERHOOD')">


		<spring:url var="editUrl"
			value="miscellaneousRecord/brotherhood/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>

		<spring:url var="deleteUrl"
			value="miscellaneousRecord/brotherhood/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out
					value="${delete}" /></a>
		</display:column>

	</security:authorize>

</display:table>

<security:authorize access="hasRole('BROTHERHOOD')">


	<spring:url var="createUrl"
		value="miscellaneousRecord/brotherhood/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>

</security:authorize>




