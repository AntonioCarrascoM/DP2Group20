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


<spring:message code="area.name" var="name" />
<spring:message code="area.pictures" var="pictures" />
<spring:message code="area.edit" var="edit" />
<spring:message code="area.delete.confirm" var="msgConfirm" />
<spring:message code="area.delete" var="delete" />
<spring:message code="area.create" var="create" />
<spring:message code="area.cancel" var="cancel" />


<jstl:set var="Administrator" value="admin" />
<%-- HandyWorker list view --%>
 

<display:table pagesize="5" class="displaytag" name="areas"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	
	
	<display:column property="name" title="${name}" sortable="true" />
	
	<display:column property="pictures" title="${pictures}" sortable="true" />
	
	

<security:authorize access="hasRole('ADMIN')">
	<%-- Edition & Delete button --%>
	
	<spring:url var="editUrl"
		value="area/administrator/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
   </display:column>
   
	<spring:url var="deleteUrl"
		value="area/administrator/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
	

	
	
</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMIN')">

	<spring:url var="createUrl" value="area/administrator/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>

</security:authorize>



