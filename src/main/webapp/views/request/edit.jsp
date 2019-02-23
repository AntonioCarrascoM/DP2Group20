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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="request.create" var="create" />
<spring:message code="request.edit" var="edit" />
<spring:message code="request.status" var="status" />
<spring:message code="request.save" var="save" />
<spring:message code="request.cancel" var="cancel" />

<!-- HAY QUE CAMBIAR LOS COLUMNACME Y ROWACME -->

<spring:message code="request.customRow" var="customRow" />
<spring:message code="request.columnRow" var="columnRow" />

<!-- HAY QUE CAMBIAR LOS COLUMNACME Y ROWACME -->
<spring:message code="request.reason" var="reason" />
<spring:message code="request.status.delete.error" var="deleteStatus" />




<spring:message code="request.reason.disclaimer"
	var="reasonDisclaimer" />

<form:form action="${requestURI}" modelAttribute="request">

	<%-- Hidden attributes --%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="procession" />
	<form:hidden path="member" />

	<%-- Edition forms --%>

	<%-- A customer receives a list of status: REJECTED and ACCEPTED. If chosen status is REJECTED, save button redirects to a view to provide a reason. --%>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${request.id != 0}">
			<jstl:if test="${request.status.name != 'ACCEPTED'}">
				<form:hidden path="customRow" />
				<form:hidden path="columnRow" />

				<form:label path="status">
					<jstl:out value="${status}" />:
		</form:label>

				<form:select path="status">
					<form:option label="ACCEPTED" value="ACCEPTED" />
					<form:option label="REJECTED" value="REJECTED" />
				</form:select>
				<form:errors cssClass="error" path="status" />
				<br />
				<br />

				<form:label path="reason">
					<jstl:out value="${reasonDisclaimer}" />:
		</form:label>
				<br />
				<br />
				<form:textarea path="reason" />
				<br />
			</jstl:if>
		</jstl:if>
	</security:authorize>


	<%-- If status is ACCEPTED, the brotherhood receives a view to input a position (row and column). --%>

	<security:authorize access="hasRole('BROTHERHOOD')">
		<jstl:if test="${request.id != 0}">
			<jstl:if test="${request.status.name == 'ACCEPTED'}">
			<form:hidden path="status" />
			<form:hidden path="reason" />

			
				<form:label path="request.customRow">
					<jstl:out value="${customRow}" />:
	</form:label>
				<form:input path="request.columnRow" />
				<form:errors cssClass="error" path="columnRow" />
				<br />

			</jstl:if>
		</jstl:if>
	</security:authorize>

	<%-- Creation form --%>

<security:authorize access="hasRole('MEMBER')">
	
		<form:hidden path="reason" />

		<form:label path="status">
			<jstl:out value="${status}" />:
			</form:label>
		<form:input path="status" />$
		<form:errors cssClass="error" path="status" />
		<br />
		

</security:authorize>
	

	<%-- Buttons --%>

	<input type="submit" name="save" value="${save}" />
			&nbsp; 
			
<security:authorize access="hasRole('BROTHERHOOD')">
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('request/brotherhood/list.do');" />
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('request/member/list.do?processionId='+ ${request.procession.id});">
</security:authorize>

</form:form>