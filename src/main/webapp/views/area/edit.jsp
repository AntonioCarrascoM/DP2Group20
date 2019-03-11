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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Stored message variables --%>

<spring:message code="area.name" var="name" />
<spring:message code="area.warning" var="warning" />
<spring:message code="area.pictures" var="pictures" />
<spring:message code="area.save" var="save" />
<spring:message code="area.cancel" var="cancel" />


<form:form action="${requestURI}" modelAttribute="area">

	<%-- Hidden attributes --%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="brotherhoods" />
	

	<%-- Edition forms --%>

	

	<security:authorize access="hasRole('ADMIN')">
		
		

		<%--<form:label path="name">
			<jstl:out value="${name}" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />--%>
		
		<acme:textbox
		 code="area.name" 
		 path="name"
		 />
		
		<form:label path="pictures">
			<jstl:out value="${pictures}" />:

		
		</form:label>
		<form:textarea path="pictures" placeholder="${warning}"/>
		<form:errors cssClass="error" path="pictures" />
		<br />




	<%-- Buttons --%>

	<input type="submit" name="save" value="${save}" />
			&nbsp; 
			

	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('area/administrator/list.do');" />



	</security:authorize>
</form:form>