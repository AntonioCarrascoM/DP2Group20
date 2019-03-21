<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="parade.ticker" var="ticker" />
<spring:message code="parade.title" var="title" />
<spring:message code="parade.description" var="description" />
<spring:message code="parade.moment" var="moment" />
<spring:message code="parade.finalMode" var="finalMode" />
<spring:message code="parade.floats" var="floats" />
<spring:message code="parade.brotherhood" var="brotherhood" />
<spring:message code="parade.maxRow" var="maxRow" />
<spring:message code="parade.maxColumn" var="maxColumn" />
<spring:message code="parade.formatDate" var="formatDate" />



<%-- For the selected parade, display the following information: --%>

<jstl:if test="${sponsorship != null}">
		<hr>
			<br />
			<a href="${sponsorship.targetURL}">
				<img src="${sponsorship.banner}" width="468" height="450">
			</a>
			<br/>
		<hr>
	</jstl:if>

	<jstl:out value="${ticker}" />:
	<jstl:out value="${parade.ticker}" />
	<br />

	<jstl:out value="${title}" />:
	<jstl:out value="${parade.title}" />
	<br />

	<jstl:out value="${description}" />:
	<jstl:out value="${parade.description}"/>
	<br />

	<jstl:out value="${maxRow}" />:
	<jstl:out value="${parade.maxRow}"/>
	<br />

	<jstl:out value="${maxColumn}" />:
	<jstl:out value="${parade.maxColumn}"/>
	<br />

	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${parade.moment}" pattern="${formatDate}"/>
	<br />

	<jstl:out value="${finalMode}" />:
	<jstl:out value="${parade.finalMode}"/>
	<br />


		<%-- Mostrar floats --%>

	<security:authorize access="hasRole('BROTHERHOOD')">

	<jstl:out value="${floats}" />:
		<spring:url var="floatsUrl" value="float/brotherhood/listByParade.do">
			<spring:param name="varId" value="${parade.id}"/>
		</spring:url>
		<a href="${floatsUrl}"><jstl:out value="${floats}" /></a>
	<br />


	</security:authorize>

	<%-- The brotherhood --%>
	<jstl:out value="${brotherhood}" />:

	<jstl:out value="${parade.brotherhood.name} ${parade.brotherhood.middleName} ${parade.brotherhood.surname}" />
	<br />
