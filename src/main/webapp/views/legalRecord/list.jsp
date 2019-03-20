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


<spring:message code="legalRecord.title" var="name" />
<spring:message code="legalRecord.description" var="description" />
<spring:message code="legalRecord.legalName" var="legalName" />
<spring:message code="legalRecord.vatNumber" var="vatNumber" />
<spring:message code="legalRecord.applicableLaws" var="applicableLaws" />
<spring:message code="legalRecord.display" var="display" />
<spring:message code="legalRecord.edit" var="edit" />
<spring:message code="legalRecord.delete.confirm" var="msgConfirm" />
<spring:message code="legalRecord.delete" var="delete" />
<spring:message code="legalRecord.create" var="create" />
<spring:message code="legalRecord.cancel" var="cancel" />



<security:authorize access="hasRole('BROTHERHOOD')"> 

<display:table pagesize="5" class="displaytag" name="legalRecords"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	
	
	<display:column property="title" title="${name}" sortable="true" />
	
	<display:column property="description" title="${description}" sortable="true" />
	
	
	

	<%-- Edition, Delete & Display button --%>
	
	<spring:url var="displayUrl"
		value="legalRecord/brotherhood/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
   </display:column>
	
	
	<spring:url var="editUrl"
		value="legalRecord/brotherhood/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
   </display:column>
   
	<spring:url var="deleteUrl"
		value="legalRecord/brotherhood/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
	
	
	
	
</display:table>


	<spring:url var="createUrl" value="legalRecord/brotherhood/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>


</security:authorize>


