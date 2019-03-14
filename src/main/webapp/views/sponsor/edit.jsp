<%--
 * edit.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="sponsor.create" var="create" />
<spring:message code="sponsor.userAccount.username" var="username" />
<spring:message code="sponsor.userAccount.password" var="password" />
<spring:message code="sponsor.name" var="name" />
<spring:message code="sponsor.middleName" var="middleName" />
<spring:message code="sponsor.surname" var="surname" />
<spring:message code="sponsor.photo" var="photo" />
<spring:message code="sponsor.email" var="email" />
<spring:message code="sponsor.phone" var="phone" />
<spring:message code="sponsor.confirm" var="confirm" />
<spring:message code="sponsor.delete" var="delete" />
<spring:message code="sponsor.delete.confirm" var="msgConf" />

<spring:message code="sponsor.address" var="address" />
<spring:message code="sponsor.save" var="save" />
<spring:message code="sponsor.cancel" var="cancel" />
<spring:message code="sponsor.phone.pattern1" var="phonePattern1" />
<spring:message code="sponsor.phone.pattern2" var="phonePattern2" />
<spring:message code="sponsor.phone.note" var="phoneNote" />

<security:authorize access="isAnonymous() or hasRole('SPONSOR')">

	<form:form id="form" action="${requestURI}" modelAttribute="sponsor">

	<%-- Forms --%>

	<form:hidden path="id" />
	
	<acme:textbox code="sponsor.name" path="name" />
	<acme:textbox code="sponsor.middleName" path="middleName" />
	<acme:textbox code="sponsor.surname" path="surname" />
	<acme:textbox code="sponsor.photo" path="photo" />
	<acme:textbox code="sponsor.email" path="email" placeholder="sponsor.phMail" />
	<acme:textbox code="sponsor.phone" path="phone" placeholder="sponsor.phPhone" />
	<acme:textbox code="sponsor.address" path="address" />
	
	<jstl:out value="${phonePattern1}" />
	<br />
	<jstl:out value="${phonePattern2}" />
	<br />
	
	<%-- Buttons --%>

	<input type="submit" name="save" value="${save}"
		onclick="return confirm('${confirm}')">&nbsp;
		
	<acme:cancel url="welcome/index.do" code="sponsor.cancel" />

	</form:form>
</security:authorize>