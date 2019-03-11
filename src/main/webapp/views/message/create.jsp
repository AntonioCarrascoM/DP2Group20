<%--
 * create.jsp
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

<%-- Stored message variables --%>

<spring:message code="message.recipient" var="recipient" />
<spring:message code="message.priority" var="priority" />
<spring:message code="message.subject" var="subject" />
<spring:message code="message.body" var="body" />
<spring:message code="message.tags" var="tags" />
<spring:message code="message.save" var="save" />
<spring:message code="message.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

<form:form action="${requestURI}" modelAttribute="msg">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:if test="${requestURI == 'message/administrator/create.do'}" >
		</jstl:if>
		
		<jstl:if test="${requestURI != 'message/administrator/create.do'}" >
			<form:label path="recipient">
				<jstl:out value="${recipient}" />:
			</form:label>
					<form:select path="recipient" >
						<form:option
							label="----"
							value="0" />
						<form:options 
							items="${recipients}" 
							itemLabel="userAccount.username"
							itemValue="id" />
					</form:select>
					<form:errors cssClass="error" path="recipient" />
			<br />
		</jstl:if>
	</security:authorize>
	
  	<security:authorize access="!hasRole('ADMIN')" >
			<form:label path="recipient">
				<jstl:out value="${recipient}" />:
			</form:label>
					<form:select path="recipient" >
						<form:option
							label="----"
							value="0" />
						<form:options 
							items="${recipients}" 
							itemLabel="userAccount.username"
							itemValue="id" />
					</form:select>
					<form:errors cssClass="error" path="recipient" />
			<br />
	</security:authorize>
	
	<form:label path="subject">
		<jstl:out value="${subject}" />:
	</form:label>
			<form:input path="subject" />
			<form:errors cssClass="error" path="subject" />
	<br />
	
	<form:label path="body">
		<jstl:out value="${body}" />:
	</form:label>
			<form:textarea path="body" />
			<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="tags">
		<jstl:out value="${tags}" />:
	</form:label>
			<form:textarea path="tags" />
			<form:errors cssClass="error" path="tags" />
	<br />
	
	<form:label path="priority">
		<jstl:out value="${priority}" />:
	</form:label>
			<form:select path="priority" >
				<form:option
					label="----"
					value="" />
				<form:options 
					items="${priorities}"/>
			</form:select>
			<form:errors cssClass="error" path="priority" />
	<br />
	
	<%-- Buttons --%>
	
	<security:authorize access="!hasRole('ADMIN')" >
		<input type="submit" name="save" value="${save}">
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')" >
		<jstl:if test="${requestURI == 'message/administrator/create.do'}" >
			<input type="submit" name="broadcast" value="${save}">
		</jstl:if>
		
		<jstl:if test="${requestURI != 'message/administrator/create.do'}" >
			<input type="submit" name="save" value="${save}">
		</jstl:if>
	</security:authorize>
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('box/list.do');" />

</form:form>
</security:authorize>