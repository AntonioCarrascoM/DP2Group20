<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="box.create" var="create" />
<spring:message code="box.edit" var="edit" />
<spring:message code="box.name" var="name" />
<spring:message code="box.parent" var="parent" />
<spring:message code="box.save" var="save" />
<spring:message code="box.delete" var="delete" />
<spring:message code="box.confirm.delete" var="confirm" />
<spring:message code="box.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}" modelAttribute="box">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="system" />
		<form:hidden path="messages" />
		<form:hidden path="actor" />
		<form:hidden path="children" />

		<form:label path="name">
			<jstl:out value="${name}" />:
	</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />

		<form:label path="parent">
			<jstl:out value="${parent}" />:
	</form:label>
		<form:select path="parent">
			<form:option label="----" value="0" />
			<form:options items="${boxes}" itemLabel="name" itemValue="id" />
		</form:select>
		<form:errors cssClass="error" path="parent" />
		<br />
		<br />
		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}" />&nbsp; 

		
	<jstl:if test="${box.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

		<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('box/list.do');" />
	</form:form>

</security:authorize>