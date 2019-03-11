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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="float.edit" var="edit" />
<spring:message code="float.save" var="save" />
<spring:message code="float.cancel" var="cancel" />
<spring:message code="float.delete" var="msgDel" />
<spring:message code="float.delete.confirm" var="msgConf" />

<spring:message code="float.confirm" var="confirm" />
<spring:message code="float.title" var="title" />
<spring:message code="float.description" var="description" />
<spring:message code="float.pictures" var="pictures" />

<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="${requestURI}" modelAttribute="f">

	<%-- Form fields --%>

	<form:hidden path="id" />
	
<%-- 	<form:label path="title">
		<jstl:out value="${title}" />:
	</form:label>
		<form:textarea path="title" />
		<form:errors cssClass="error" path="title" />
	<br /> 
	
	<form:label path="description">
		<jstl:out value="${description}" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="pictures">
	<jstl:out value="${pictures}" />:
	</form:label>
	<form:textarea path="pictures" placeholder="${warning}"/>
	<form:errors cssClass="error" path="pictures" />
	<br /> 
--%>
	
	<acme:textbox code="float.title" path="title" />
	<br />
	
	<acme:textarea code="float.description" path="description" />
	<br />
	
	<acme:textarea code="float.pictures" path="pictures" placeholder="float.ph"/>
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="float.save" />
		
		<jstl:if test="${f.id != 0}">
			<input type="submit" name="delete" value="${msgDel}"
				onclick="return confirm('${msgConf}')">
		</jstl:if>
		
	<acme:cancel url="welcome/index.do" code="finder.cancel" />
		

<%-- 	<input type="submit" name="save" value="${save}" onclick="return confirm('${confirm}')"/>&nbsp;	
	 	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" /> --%>

</form:form>

</security:authorize>

