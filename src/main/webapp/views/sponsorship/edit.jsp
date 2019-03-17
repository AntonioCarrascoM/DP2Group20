<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="sponsorship.create" var="create" />
<spring:message code="sponsorship.edit" var="edit" />
<spring:message code="sponsorship.delete" var="delete" />
<spring:message code="sponsorship.confirm.delete" var="confirm" />
<spring:message code="sponsorship.banner" var="banner" />
<spring:message code="sponsorship.targetURL" var="targetURL" />
<spring:message code="sponsorship.sponsor" var="sponsor" />
<spring:message code="sponsorship.parade" var="parade" />
<spring:message code="sponsorship.creditCard.holder" var="holder" />
<spring:message code="sponsorship.creditCard.make" var="make" />
<spring:message code="sponsorship.creditCard.number" var="number" />
<spring:message code="sponsorship.creditCard.expMonth" var="expMonth" />
<spring:message code="sponsorship.creditCard.expYear" var="expYear" />
<spring:message code="sponsorship.creditCard.cvv" var="cvv" />
<spring:message code="sponsorship.save" var="save" />
<spring:message code="sponsorship.cancel" var="cancel" />

<security:authorize access="hasRole('SPONSOR')">

<form:form action="${requestURI}" modelAttribute="sponsorship">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	
	<acme:textbox code="sponsorship.banner" path="banner" />
	<br />
	<acme:textbox code="sponsorship.targetURL" path="targetURL" />
	<br />

	<acme:textbox code="sponsorship.creditCard.holder" path="creditCard.holder" />
	<br />
	<form:label path="creditCard.make">
			<jstl:out value="${make}" />:
	</form:label>

		<form:select path="creditCard.make">
			<form:option label="----" value="0" />
			<form:options items="${makes}" />
		</form:select>

		<form:errors cssClass="error" path="creditCard.make" />
		<br />
	
	<acme:textbox code="sponsorship.creditCard.number" path="creditCard.number" />
	<br />
	<acme:textbox code="sponsorship.creditCard.expMonth" path="creditCard.expMonth" />
	<br />
	<acme:textbox code="sponsorship.creditCard.expYear" path="creditCard.expYear" />
	<br />
	<acme:textbox code="sponsorship.creditCard.cvv" path="creditCard.cvv" />
	<br />

	<form:label path="parade">
			<jstl:out value="${parade}" />:
	</form:label>

		<form:select path="parade">
			<form:option label="----" value="0" />
			<form:options items="${parades}" itemLabel="title"/>
		</form:select>

		<form:errors cssClass="error" path="parade" />
		<br />
	<%-- Buttons --%>
	<input type="submit" name="save" value="${save}"/>&nbsp; 
	
	<jstl:if test="${sponsorship.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>
	
		<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />

</form:form>

</security:authorize>