
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

<%-- Stored message variables --%>
<security:authorize access="hasRole('MEMBER')">

	<spring:message code="finder.keyWord" var="msgKeyWord" />
	<spring:message code="finder.minimumDate" var="msgMinimumDate" />
	<spring:message code="finder.maximumDate" var="msgMaximumDate" />
	<spring:message code="finder.area" var="msgArea" />
	<spring:message code="finder.save" var="msgSave" />
	<spring:message code="finder.cancel" var="msgCancel" />


	<form:form action="${requestURI}" modelAttribute="finder">

	<%-- Form fields --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="processions" />
	<form:hidden path="moment" />

		<%-- 	<form:label path="keyWord">
		<jstl:out value="${msgKeyWord}" />:
	</form:label>
	<form:input path="keyWord"/>
	<form:errors cssClass="error" path="keyWord"/>
	<br /> --%>


		<%-- 	<form:label path="area">
		<jstl:out value="${msgArea}" />:
	</form:label>
			<form:select path="area" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${areas}" 
					itemLabel="name"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="area" />
	<br /> --%>

	<%-- 	<form:label path="minimumDate">
	<jstl:out value="${msgMinimumDate}" />:
	</form:label>
	<form:input path="minimumDate"/>
	<form:errors cssClass="error" path="minimumDate"/>
	<br /> --%>

	<%-- 	<form:label path="maximumDate">
	<jstl:out value="${msgMaximumDate}" />:
	</form:label>
	<form:input path="maximumDate"/>
	<form:errors cssClass="error" path="maximumDate"/>
	<br /> --%>

	<acme:textbox code="finder.keyWord" path="keyWord" />
	<br />
	
	<acme:select code="finder.area" path="area" items="${areas}"
		itemLabel="name" id="areas" />
	<br />
	
	<acme:textbox code="finder.minimumDate" path="minimumDate" placeholder="finder.ph"/>
	<br />
	
	<acme:textbox code="finder.maximumDate" path="maximumDate" placeholder="finder.ph"/>
	<br />
	
	<%-- Buttons --%>

	<%-- 	<input type="submit" name="save" value="${msgSave}"/> --%>
	<%-- 	<input type="button" name="cancel"
		value="${msgCancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" /> --%>
		
	<acme:submit name="save" code="finder.save" />
	<acme:cancel url="welcome/index.do" code="finder.cancel" />

	</form:form>
</security:authorize>