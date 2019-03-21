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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="proclaim.cancel" var="cancel" />

<spring:message code="proclaim.description" var="description" />
<spring:message code="proclaim.publicationMoment" var="publicationMoment" />
<spring:message code="proclaim.save.confirm" var="msgConf" />
<spring:message code="proclaim.save" var="save" />








<security:authorize access="hasRole('CHAPTER')">

<form:form action="${requestURI}" modelAttribute="proclaim">

	<%-- Form fields --%>

	<form:hidden path="id" />
	
	<acme:textarea
		 code="proclaim.description" 
		 path="description"/>
		 <br />
		 
	<acme:textbox 
	     placeholder="proclaim.ph"
		 code = "proclaim.publicationMoment" 
		 path="publicationMoment"/>
		 <br/>
		 
			<%-- Buttons --%>
		
		<%-- <acme:submit code="proclaim.save" name="save"/> --%>
		<input type="submit" name="save" value="${save}"
					onclick="return confirm('${msgConf}')">
			
		<acme:cancel code="proclaim.cancel" url ="/proclaim/chapter/list.do" />
			

</form:form>

</security:authorize>