<%--
 * list.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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

<spring:message code="sponsorship.create" var="create" />
<spring:message code="sponsorship.edit" var="edit" />
<spring:message code="sponsorship.delete" var="delete" />
<spring:message code="sponsorship.save" var="save" />
<spring:message code="sponsorship.cancel" var="cancel" />
<spring:message code="sponsorship.display" var="display" />
<spring:message code="sponsorship.confirm.delete" var="confirm" />
<spring:message code="sponsorship.banner" var="banner" />
<spring:message code="sponsorship.targetURL" var="targetURL" />
<spring:message code="sponsorship.isActive" var="isActive" />
<spring:message code="sponsorship.parade" var="parade" />
<spring:message code="sponsorship.sponsor" var="sponsor" />

<spring:message code="sponsorship.remove" var="msgRemove" />
<spring:message code="sponsorship.remove.error" var="msgRemoveError" />
<spring:message code="sponsorship.activate" var="msgActivate" />
<spring:message code="sponsorship.activate.error" var="msgActivateError" />
<spring:message code="sponsorship.remove.confirm" var="msgConfirm" />



<security:authorize access="hasRole('SPONSOR')">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="sponsorships" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column property="banner" title="${banner}" sortable="true" />

	<display:column property="targetURL" title="${targetURL}" sortable="true" />
	
	<display:column property="parade.title" title="${parade}" sortable="true" />
	
	<display:column property="isActive" title="${isActive}" sortable="true" />
	
	<%-- Edit --%>	
		<spring:url var="editUrl" value="sponsorship/sponsor/edit.do">
			<spring:param name="sponsorshipId" value="${row.id}" />
		</spring:url>
		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>		

	<!--  Activate sponsorship -->

	<spring:url var="activateURL" value="sponsorship/sponsor/activate.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgActivate}">
	<jstl:if test="${row.isActive == false}">
		<a href="${activateURL}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgActivate}" /></a>
		</jstl:if>
	</display:column>
	
		<!--  Remove sponsorship -->

	<spring:url var="removeURL" value="sponsorship/sponsor/remove.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgRemove}">
	<jstl:if test="${row.isActive == true}">
		<a href="${removeURL}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgRemove}" /></a>
		</jstl:if>
	</display:column>

</display:table>

		<spring:url var="createUrl" value="sponsorship/sponsor/create.do"/>
		<a href="${createUrl}"><jstl:out value="${create}"/></a>

</security:authorize>