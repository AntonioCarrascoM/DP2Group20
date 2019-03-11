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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="brotherhood.create" var="create" />
<spring:message code="brotherhood.userAccount.username" var="username" />
<spring:message code="brotherhood.userAccount.password" var="password" />
<spring:message code="brotherhood.name" var="name" />
<spring:message code="brotherhood.middleName" var="middleName" />
<spring:message code="brotherhood.surname" var="surname" />
<spring:message code="brotherhood.photo" var="photo" />
<spring:message code="brotherhood.email" var="email" />
<spring:message code="brotherhood.phone" var="phone" />
<spring:message code="brotherhood.address" var="address" />
<spring:message code="brotherhood.area" var="msgArea" />
<spring:message code="brotherhood.title" var="title" />
<spring:message code="brotherhood.establishmentDate" var="date" />
<spring:message code="brotherhood.pictures" var="pictures" />
<spring:message code="brotherhood.save" var="save" />
<spring:message code="brotherhood.cancel" var="cancel" />
<spring:message code="brotherhood.confirm" var="confirm" />
<spring:message code="brotherhood.terms" var="terms" />
<spring:message code="brotherhood.acceptedTerms" var="acceptedTerms" />
<spring:message code="brotherhood.secondPassword" var="secondPassword" />
<spring:message code="brotherhood.phone.pattern1" var="phonePattern1" />
<spring:message code="brotherhood.phone.pattern2" var="phonePattern2" />
<spring:message code="brotherhood.phone.note" var="phoneNote" />

<security:authorize access="isAnonymous() or hasRole('BROTHERHOOD')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="fob">

		<%-- Forms --%>

			<form:label path="username">
				<jstl:out value="${username}" />:
		</form:label>
			<form:input path="username" />
			<form:errors cssClass="error" path="username" />
			<br />

			<form:label path="password">
				<jstl:out value="${password}" />:
		</form:label>
			<form:password path="password" />
			<form:errors cssClass="error" path="password" />
			<br />
			
			<form:label path="secondPassword">
				<jstl:out value="${secondPassword}" />:
		</form:label>
			<form:password path="secondPassword" />
			<form:errors cssClass="error" path="secondPassword" />
			<br />
		<acme:textbox code="brotherhood.name" path="name"/>
		<acme:textbox code="brotherhood.middleName" path="middleName"/>
		<acme:textbox code="brotherhood.surname" path="surname"/>
		<acme:textbox code="brotherhood.photo" path="photo"/>
		<acme:textbox code="brotherhood.email" path="email" placeholder="mail.ph"/>
		<acme:textbox code="brotherhood.phone" path="phone"/>
		<acme:textbox code="brotherhood.address" path="address"/>
		<acme:textbox code="brotherhood.title" path="title"/>
		<acme:textbox code="brotherhood.establishmentDate" path="establishmentDate" placeholder="date.ph"/>
		<acme:textarea code="brotherhood.pictures" path="pictures" placeholder="brotherhood.warning"/>
		
		<br>
		<form:label path="acceptedTerms" >
        	<jstl:out value="${acceptedTerms}" />:
    </form:label>	
 
    <a href="welcome/terms.do" target="_blank"><jstl:out value="${terms}" /></a>
    <form:checkbox path="acceptedTerms" required="required"/>
    <form:errors path="acceptedTerms" cssClass="error" />
    <br/>
    	<br>
    

		 <jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />
		

		<%-- Buttons --%>

		<input type="submit" name="create" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
	
		<acme:cancel url="welcome/index.do" code="brotherhood.cancel" />

	</form:form>
</security:authorize>