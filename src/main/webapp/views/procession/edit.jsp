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

<spring:message code="procession.edit" var="edit" />
<spring:message code="procession.save" var="save" />
<spring:message code="procession.cancel" var="cancel" />
<spring:message code="procession.title" var="title" />
<spring:message code="procession.description" var="description" />
<spring:message code="procession.moment" var="msgMoment" />
<spring:message code="procession.maxColumn" var="maxColumn" />
<spring:message code="procession.maxRow" var="maxRow" />

<spring:message code="procession.finalMode" var="finalMode" />

<spring:message code="procession.save" var="msgSave" />
<spring:message code="procession.delete" var="msgDel" />
<spring:message code="procession.delete.confirm" var="msgConf" />
<spring:message code="procession.cancel" var="msgCancel" />




<security:authorize access="hasRole('BROTHERHOOD')">

<form:form action="${requestURI}" modelAttribute="procession">

	<%-- Form fields --%>

	<form:hidden path="id" />
	
<%-- 	<form:label path="title">
		<jstl:out value="${title}" />:
	</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
	<br />
	
	
	<form:label path="description">
		<jstl:out value="${description}" />:
	</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="maxRow">
		<jstl:out value="${maxRow}" />:
	</form:label>
		<form:input path="maxRow" />
		<form:errors cssClass="error" path="maxRow" />
	<br />
	
	<form:label path="maxColumn">
		<jstl:out value="${maxColumn}" />:
	</form:label>
		<form:input path="maxColumn" />
		<form:errors cssClass="error" path="maxColumn" />
	<br />
	
	<form:label path="moment">
			<jstl:out value="${msgMoment}" />:
	</form:label>
		<form:input path="moment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="moment" />
		<br />
		<br /> --%>
	
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
		 code="procession.title" 
		 path="title"/>
		 <br />
		 
	<acme:textarea
		 code="procession.description" 
		 path="description"/>
		 <br />
		 
	<acme:textbox
		 code="procession.maxRow" 
		 path="maxRow"/>
		 <br />
	<acme:textbox
		 code="procession.maxColumn" 
		 path="maxColumn"/>
		 <br />
		 
	<acme:textbox 
	     placeholder="procession.ph"
		 code = "procession.moment" 
		 path="moment"/>
		 <br/>
		 
		 
	
	
	
	
	
	
	
	
	
	
			<%-- Buttons --%>
<!-- A brotherhood cannot organise any processions until they selected an area -->
	<jstl:if test="${not empty procession.brotherhood.area}">
		<%-- <input type="submit" name="save" value="${msgSave}"> --%>
		
				<acme:submit code="procession.save" name="save"/>
		

		<jstl:if test="${procession.id != 0}">
			<input type="submit" name="delete" value="${msgDel}"
				onclick="return confirm('${msgConf}')">
		</jstl:if>

		<%-- <input type="button" name="cancel" value="${msgCancel}"
			onclick="javascript: relativeRedir('procession/brotherhood/list.do');" /> --%>
			
		<acme:cancel code="procession.cancel" url ="/procession/brotherhood/list.do" />
			
	</jstl:if>
</form:form>

</security:authorize>