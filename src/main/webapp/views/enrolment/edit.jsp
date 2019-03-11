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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:set var="localeCode" value="${pageContext.response.locale}"/>

<%-- Stored message variables --%>

<spring:message code="enrolment.position" var="msgPosition" />
<spring:message code="enrolment.save" var="msgSave" />
<spring:message code="enrolment.cancel" var="msgCancel" />


<form:form action="${requestURI}" modelAttribute="enrolment">
	
	<%-- Form fields --%>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="brotherhood"/>
	<form:hidden path="member"/>
	<form:hidden path="moment"/>
	<form:hidden path="brotherhoodToMember"/>
	<form:hidden path="dropOutMoment"/>

<jstl:if test="${localeCode == 'en'}">
	<form:label path="position">
		<jstl:out value="${msgPosition}" />:
	</form:label>
			<form:select path="position" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${positions}" 
					itemLabel="nameEN"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="position" />
	<br />
</jstl:if>	

<jstl:if test="${localeCode == 'es'}">
	<form:label path="position">
		<jstl:out value="${msgPosition}" />:
	</form:label>
			<form:select path="position" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${positions}" 
					itemLabel="nameES"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="position" />
	<br />
</jstl:if>	
	<%-- Buttons --%>
	
	<input type="submit" name="save" value="${msgSave}"/>
	
	<input type="button" name="cancel"
		value="${msgCancel}"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
