<%--
 * edit.jsp
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

<spring:message code="configuration.systemName" var="systemName" />
<spring:message code="configuration.banner" var="banner" />
<spring:message code="configuration.welcomeEN" var="welcomeEN" />
<spring:message code="configuration.welcomeES" var="welcomeES" />
<spring:message code="configuration.spamWords" var="spamWords" />
<spring:message code="configuration.countryCode" var="countryCode" />
<spring:message code="configuration.expireFinderMinutes" var="expireFinderMinutes" />
<spring:message code="configuration.maxFinderResults" var="maxFinderResults" />
<spring:message code="configuration.positiveWords" var="positiveWords" />
<spring:message code="configuration.negativeWords" var="negativeWords" />
<spring:message code="configuration.priorityList" var="priorityList" />

<spring:message code="configuration.save" var="save" />
<spring:message code="configuration.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

<form:form action="${requestURI}" modelAttribute="configuration">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
		
	<form:label path="systemName"><jstl:out value="${systemName}" />:</form:label>
	<form:input path="systemName" />
	<form:errors cssClass="error" path="systemName" />
	<br/><br/>
	
	<form:label path="banner"><jstl:out value="${banner}" />:</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br/><br/>
	
	<form:label path="welcomeEN"><jstl:out value="${welcomeEN}" />:</form:label>
	<form:textarea path="welcomeEN" />
	<form:errors cssClass="error" path="welcomeEN" />
	<br/><br/>
	
	<form:label path="welcomeES"><jstl:out value="${welcomeES}" />:</form:label>
	<form:textarea path="welcomeES" />
	<form:errors cssClass="error" path="welcomeES" />
	<br/><br/>
	
	
	
	<form:label path="spamWords"><jstl:out value="${spamWords}" />:</form:label>
	<form:textarea path="spamWords" />
	<form:errors cssClass="error" path="spamWords" />
	<br/><br/>
	
	<form:label path="countryCode"><jstl:out value="${countryCode}" />:</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br/><br/>
	
	
	
	<form:label path="expireFinderMinutes"><jstl:out value="${expireFinderMinutes}" />:</form:label>
	<form:input path="expireFinderMinutes" />
	<form:errors cssClass="error" path="expireFinderMinutes" />
	<br/><br/>
	
	<form:label path="maxFinderResults"><jstl:out value="${maxFinderResults}" />:</form:label>
	<form:input path="maxFinderResults" />
	<form:errors cssClass="error" path="maxFinderResults" />
	<br/><br/>	

	<form:label path="positiveWords"><jstl:out value="${positiveWords}" />:</form:label>
	<form:input path="positiveWords" />
	<form:errors cssClass="error" path="positiveWords" />
	<br/><br/>	
	
	<form:label path="negativeWords"><jstl:out value="${negativeWords}" />:</form:label>
	<form:input path="negativeWords" />
	<form:errors cssClass="error" path="negativeWords" />
	<br/><br/>		
	
	<form:label path="priorityList"><jstl:out value="${priorityList}" />:</form:label>
	<form:input path="priorityList" />
	<form:errors cssClass="error" path="priorityList" />
	<br/><br/>		
	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${save}">
		
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('configuration/administrator/display.do');" />

</form:form>

</security:authorize>