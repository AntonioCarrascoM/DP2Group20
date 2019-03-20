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

<spring:message code="chapter.create" var="create" />
<spring:message code="chapter.userAccount.username" var="username" />
<spring:message code="chapter.userAccount.password" var="password" />
<spring:message code="chapter.name" var="name" />
<spring:message code="chapter.middleName" var="middleName" />
<spring:message code="chapter.surname" var="surname" />
<spring:message code="chapter.photo" var="photo" />
<spring:message code="chapter.email" var="email" />
<spring:message code="chapter.phone" var="phone" />
<spring:message code="chapter.address" var="address" />
<spring:message code="chapter.area" var="msgArea" />
<spring:message code="chapter.title" var="title" />
<spring:message code="chapter.save" var="save" />
<spring:message code="chapter.cancel" var="cancel" />
<spring:message code="chapter.confirm" var="confirm" />
<spring:message code="chapter.terms" var="terms" />
<spring:message code="chapter.acceptedTerms" var="acceptedTerms" />
<spring:message code="chapter.secondPassword" var="secondPassword" />
<spring:message code="chapter.phone.pattern1" var="phonePattern1" />
<spring:message code="chapter.phone.pattern2" var="phonePattern2" />
<spring:message code="chapter.phone.note" var="phoneNote" />

<security:authorize access="isAnonymous()">

	<form:form id="form" action="${requestURI}"
		modelAttribute="foc">

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
		<acme:textbox code="chapter.name" path="name"/>
		<acme:textbox code="chapter.middleName" path="middleName"/>
		<acme:textbox code="chapter.surname" path="surname"/>
		<acme:textbox code="chapter.photo" path="photo"/>
		<acme:textbox code="chapter.email" path="email" placeholder="mail.ph"/>
		<acme:textbox code="chapter.phone" path="phone"/>
		<acme:textbox code="chapter.address" path="address"/>
		<acme:textbox code="chapter.title" path="title"/>
		
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
	
		<acme:cancel url="welcome/index.do" code="chapter.cancel" />

	</form:form>
</security:authorize>