<%--
 * list.jsp
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

<spring:message code="enrolment.moment" var="msgMoment" />
<spring:message code="enrolment.dropOutMoment" var="msgDropOutMoment" />
<spring:message code="enrolment.member" var="msgMember" />
<spring:message code="enrolment.member.surname" var="msgMemberSurname" />
<spring:message code="enrolment.brotherhood.name" var="msgMemberName" />
<spring:message code="enrolment.position" var="msgPosition" />
<spring:message code="enrolment.drop" var="msgDrop" />
<spring:message code="enrolment.drop.error" var="msgDropError" />
<spring:message code="enrolment.drop.confirm" var="msgConfirm" />
<spring:message code="enrolment.delete" var="msgDelete" />
<spring:message code="enrolment.formatDate" var="formatDate" />

<jstl:set var="localeCode" value="${pageContext.response.locale}"/>

<display:table pagesize="5" class="displaytag" name="enrolments" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="member.name" title="${msgMember}" sortable="true" />
	<display:column property="member.surname" title="${msgMemberSurname}" sortable="true" />
	<display:column property="brotherhood.name" title="${msgMemberName}" sortable="true" />

	<jstl:if test="${localeCode == 'en'}">
	<display:column property="position.nameEN" title="${msgPosition}" sortable="true" />
	</jstl:if>
	<jstl:if test="${localeCode == 'es'}">
	<display:column property="position.nameES" title="${msgPosition}" sortable="true" />
	</jstl:if>

	<display:column title="${msgMoment}" sortable="true" >
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>

	<display:column title="${msgDropOutMoment}" sortable="true" >
		<fmt:formatDate value="${row.dropOutMoment}" pattern="${formatDate}" />
	</display:column>


	<security:authorize access="hasRole('BROTHERHOOD')">

	<!--  Drop out of an enrolment -->

	<spring:url var="dropUrl" value="enrolment/brotherhood/drop.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${msgDrop}">
	<jstl:if test="${empty row.dropOutMoment}">
		<a href="${dropUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDrop}" /></a>
		</jstl:if>
	</display:column>

	<!--  Deleting enrolment -->

	<spring:url var="deleteUrl" value="enrolment/brotherhood/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${msgDelete}">
	<jstl:if test="${not empty row.dropOutMoment}">
		<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDelete}" /></a>
		</jstl:if>
	</display:column>

	</security:authorize>


	<security:authorize access="hasRole('MEMBER')">

	<!--  Drop out of an enrolment -->

	<spring:url var="dropUrl" value="enrolment/member/drop.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${msgDrop}">
	<jstl:if test="${empty row.dropOutMoment}">
		<a href="${dropUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDrop}" /></a>
		</jstl:if>
	</display:column>

	<!--  Deleting enrolment -->

	<spring:url var="deleteUrl" value="enrolment/member/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	<display:column title="${msgDelete}">
	<jstl:if test="${not empty row.dropOutMoment}">
		<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDelete}" /></a>
		</jstl:if>
	</display:column>

	</security:authorize>


</display:table>
