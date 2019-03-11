
<%--
 * create.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:set var="localeCode" value="${pageContext.response.locale}" />

<%-- Stored message variables --%>

<spring:message code="enrolment.position" var="msgPosition" />
<spring:message code="enrolment.save" var="msgSave" />
<spring:message code="enrolment.cancel" var="msgCancel" />


<form:form action="${requestURI}" modelAttribute="enrolment">

	<%-- Form fields --%>
	<form:hidden path="id" />
	<form:hidden path="member" />
	
	<jstl:if test="${localeCode == 'en'}">

		<acme:select code="enrolment.position" path="position"
			items="${positions}" itemLabel="nameEN" id="positions" />
		<br />

	</jstl:if>

	<jstl:if test="${localeCode == 'es'}">

		<acme:select code="enrolment.position" path="position"
			items="${positions}" itemLabel="nameES" id="positions" />
		<br />
	</jstl:if>
	
	<%-- Buttons --%>
	<acme:submit name="save" code="enrolment.save" />

	<acme:cancel url="welcome/index.do" code="enrolment.cancel" />

</form:form>
