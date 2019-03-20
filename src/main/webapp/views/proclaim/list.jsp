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


<spring:message code="proclaim.publicationMoment" var="moment" />
<spring:message code="proclaim.formatDate" var="formatDate" />
<spring:message code="proclaim.create" var="msgCreate" />


<%-- Proclaim list view --%>

<display:table pagesize="5" class="displaytag" name="proclaims"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="description" title="${description}" sortable="true" />
	
	
	<display:column title="${publicationMoment}" sortable="true">
		<fmt:formatDate value="${row.publicationMoment}" pattern="${formatDate}" />
	</display:column>	
		

</display:table>


<security:authorize access="hasRole('CHAPTER')">

	<spring:url var="createUrl" value="proclaim/chapter/create.do"/>
	<a href="${createUrl}"><jstl:out value="${msgCreate}"/></a>
	
</security:authorize>	
