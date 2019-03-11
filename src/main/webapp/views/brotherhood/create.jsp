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
		
		<form:label path="name">
			<jstl:out value="${name}" />:
	</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br />

		<form:label path="middleName">
			<jstl:out value="${middleName}" />:
	</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br />

		<form:label path="surname">
			<jstl:out value="${surname}" />:
	</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br />


		<form:label path="photo">
			<jstl:out value="${photo}" />:
	</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br />

		<form:label path="email">
			<jstl:out value="${email}" />:
	</form:label>
		<form:input path="email" placeholder="mail@mail.com" />
		<form:errors cssClass="error" path="email" />
		<br />

		<form:label path="phone">
			<jstl:out value="${phone}" />:
	</form:label>
		<form:input path="phone" placeholder="+CC 654654654" />
		<form:errors cssClass="error" path="phone" />
		<jstl:out value="${phoneNote}" />
		<br />

		<form:label path="address">
			<jstl:out value="${address}" />:
	</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />

		<form:label path="title">
			<jstl:out value="${title}" />:
	</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />

		<form:label path="establishmentDate">
			<jstl:out value="${date}" />:
	</form:label>
		<form:input path="establishmentDate" placeholder="dd/MM/yyyy"/>
		<form:errors cssClass="error" path="establishmentDate" />
		<br />

		<form:label path="pictures">
			<jstl:out value="${pictures}" />:
	</form:label>
		<form:textarea path="pictures" placeholder="${warning}"/>
		<form:errors cssClass="error" path="pictures" />
		<br />
		
		<form:label path="acceptedTerms" >
        	<jstl:out value="${acceptedTerms}" />:
    </form:label>
    <a href="welcome/terms.do" target="_blank"><jstl:out value="${terms}" /></a>
    <form:checkbox path="acceptedTerms" required="required"/>
    <form:errors path="acceptedTerms" cssClass="error" />
    <br/>

		

		<%-- Buttons --%>

		<input type="submit" name="create" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
		
	<input type="button" name="cancel" value="${cancel}"
			onclick="javascript: relativeRedir('welcome/index.do');" />

	</form:form>
</security:authorize>