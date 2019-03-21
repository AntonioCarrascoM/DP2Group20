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


<spring:message code="periodRecord.title" var="name" />
<spring:message code="periodRecord.description" var="description" />
<spring:message code="periodRecord.edit" var="edit" />
<spring:message code="periodRecord.display" var="display" />
<spring:message code="periodRecord.delete.confirm" var="msgConfirm" />
<spring:message code="periodRecord.delete" var="delete" />
<spring:message code="periodRecord.create" var="create" />
<spring:message code="periodRecord.cancel" var="cancel" />




<security:authorize access="hasRole('BROTHERHOOD')"> 

<display:table pagesize="5" class="displaytag" name="periodRecords"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	
	
	<display:column property="title" title="${name}" sortable="true" />
	
	<display:column property="description" title="${description}" sortable="true" />
	
	
	

	<%-- Edition, Delete & Display button --%>
	
	<spring:url var="displayUrl"
		value="periodRecord/brotherhood/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
   </display:column>
	
	<spring:url var="editUrl"
		value="periodRecord/brotherhood/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
   </display:column>
   
	<spring:url var="deleteUrl"
		value="periodRecord/brotherhood/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
	
	
	
	
</display:table>


	<spring:url var="createUrl" value="periodRecord/brotherhood/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>


</security:authorize>


