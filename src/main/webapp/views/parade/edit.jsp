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

<spring:message code="parade.edit" var="edit" />
<spring:message code="parade.save" var="save" />
<spring:message code="parade.cancel" var="cancel" />
<spring:message code="parade.title" var="title" />
<spring:message code="parade.description" var="description" />
<spring:message code="parade.moment" var="msgMoment" />
<spring:message code="parade.maxColumn" var="maxColumn" />
<spring:message code="parade.maxRow" var="maxRow" />
<spring:message code="parade.rejectionReason" var="rejectionReason" />
<spring:message code="parade.paradeStatus" var="paradeStatus" />

<spring:message code="parade.finalMode" var="finalMode" />

<spring:message code="parade.save" var="msgSave" />
<spring:message code="parade.delete" var="msgDel" />
<spring:message code="parade.delete.confirm" var="msgConf" />
<spring:message code="parade.cancel" var="msgCancel" />






<form:form action="${requestURI}" modelAttribute="parade">
<security:authorize access="hasRole('BROTHERHOOD')">
	<%-- Form fields --%>

	<form:hidden path="id" />
	
	
	<form:label path="finalMode">
		<jstl:out value="${finalMode}" />:
	</form:label>
			<form:select path="finalMode" >
				<form:option
					label="NO"
					value="false" />
				<form:option
					label="YES"
					value="true" />
			</form:select>
	<br/><br/>
	
	
	<acme:textbox
		 code="parade.title" 
		 path="title"/>
		 <br />
		 
	<acme:textarea
		 code="parade.description" 
		 path="description"/>
		 <br />
		 
	<acme:textbox
		 code="parade.maxRow" 
		 path="maxRow"/>
		 <br />
	<acme:textbox
		 code="parade.maxColumn" 
		 path="maxColumn"/>
		 <br />
		 
	<acme:textbox 
	     placeholder="parade.ph"
		 code = "parade.moment" 
		 path="moment"/>
		 <br/>

	</security:authorize>
	<security:authorize access="hasRole('CHAPTER')">
	
	<form:hidden path="id" />
	
	<jstl:if test="${parade.paradeStatus.name == 'SUBMITTED'}">
	<form:label path="paradeStatus">
					<jstl:out value="${paradeStatus}" />:
		</form:label>
		
				<form:select path="paradeStatus">
					<form:option label="ACCEPTED" value="ACCEPTED" />
					<form:option label="REJECTED" value="REJECTED" />
				</form:select>
				<form:errors cssClass="error" path="paradeStatus" />
				<br />
				<br />
		</jstl:if>
		<jstl:if test="${empty parade.rejectionReason and parade.paradeStatus.name == 'REJECTED'}">	
			<acme:textarea
		 		code = "parade.rejectionReason" 
		 		path="rejectionReason"/>
		 		<br/>
		</jstl:if>	
		
	
	</security:authorize>
	
			<%-- Buttons --%>
<!-- A brotherhood cannot organise any parades until they selected an area -->
		
		<acme:submit code="parade.save" name="save"/>
		
		
		<jstl:if test="${parade.id != 0 and parade.finalMode eq false}">
			<input type="submit" name="delete" value="${msgDel}"
				onclick="return confirm('${msgConf}')">
		</jstl:if>

		<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:cancel code="parade.cancel" url ="/parade/brotherhood/list.do" />
		</security:authorize>
			
</form:form>

