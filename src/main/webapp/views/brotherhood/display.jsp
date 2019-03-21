<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>
<spring:message code="brotherhood.name" var="msgName" />
<spring:message code="brotherhood.middleName"  var="msgMiddleName"/>
<spring:message code="brotherhood.surname" var="msgSurname" />
<spring:message code="brotherhood.photo" var="msgPhoto" />
<spring:message code="brotherhood.email" var="msgEmail" />
<spring:message code="brotherhood.phone" var="msgPhone" />
<spring:message code="brotherhood.address" var="msgAddress" />
<spring:message code="brotherhood.score" var="msgScore" />
<spring:message code="brotherhood.title" var="msgTitle" />
<spring:message code="brotherhood.establishmentDate"  var="msgEstablishmentDate"/>
<spring:message code="brotherhood.pictures" var="msgPictures" />
<spring:message code="brotherhood.area" var="msgArea" />
<spring:message code="brotherhood.parades" var="msgParades" />
<spring:message code="brotherhood.members" var="msgMembers" />
<spring:message code="brotherhood.floats" var="msgFloats" />
<spring:message code="brotherhood.inceptionRecords" var="inceptionRecords" />
<spring:message code="brotherhood.legalRecords" var="legalRecords" />
<spring:message code="brotherhood.periodRecords" var="periodRecords" />
<spring:message code="brotherhood.miscellaneousRecords" var="miscellaneousRecords" />
<spring:message code="brotherhood.linkRecords" var="linkRecords" />
<spring:message code="brotherhood.brotherhoodHistory" var="brotherhoodHistory" />
<spring:message code="brotherhood.return" var="msgReturn" />


<%-- Display the following information about the audit record: --%>
	
	<jstl:out value="${msgName}" />:
	<jstl:out value="${brotherhood.name}" />
	<br /> 
	
	<jstl:out value="${msgMiddleName}" />:
	<jstl:out value="${brotherhood.middleName}" />
	<br />
	
	<jstl:out value="${msgSurname}" />:
	<jstl:out value="${brotherhood.surname}" />
	<br />
	
	<jstl:out value="${msgPhoto}" />:
	<a href="${brotherhood.photo}"><jstl:out value="${brotherhood.photo}" /></a>
	<br />
	
	<jstl:out value="${msgEmail}" />:
	<jstl:out value="${brotherhood.email}" />
	<br />
	
	<jstl:out value="${msgPhone}" />:
	<jstl:out value="${brotherhood.phone}" />
	<br />
	
	<jstl:out value="${msgAddress}" />:
	<jstl:out value="${brotherhood.address}" />
	<br />
	
	<security:authorize access="hasRole('ADMIN')">
	<jstl:out value="${msgScore}" />:
	<jstl:out value="${brotherhood.score}" />
	<br />
	</security:authorize>
	
	<jstl:out value="${msgTitle}" />:
	<jstl:out value="${brotherhood.title}" />
	<br />
	
	<jstl:out value="${msgEstablishmentDate}" />:
	<jstl:out value="${brotherhood.establishmentDate}" />
	<br />
	
	<jstl:out value="${msgArea}" />:
	<jstl:out value="${brotherhood.area.name}" />
	<br />
	
	<jstl:out value="${msgPictures}" />:
	<jstl:out value="${brotherhood.pictures}" />
	<br />
	
	
	
	
	<spring:url var="membersUrl"
		value="member/listByBrotherhood.do">
		<spring:param name="varId"
			value="${brotherhood.id}"/>
		</spring:url>
		<a href="${membersUrl}"><jstl:out value="${msgMembers}" /></a>
	<br />
	
	<spring:url var="paradesUrl"
		value="parade/listByBrotherhood.do">
		<spring:param name="varId"
			value="${brotherhood.id}"/>
		</spring:url>
		<a href="${paradesUrl}"><jstl:out value="${msgParades}" /></a>
	<br />
	
	<spring:url var="floatsUrl"
		value="float/listByBrotherhood.do">
		<spring:param name="varId"
			value="${brotherhood.id}"/>
		</spring:url>
		<a href="${floatsUrl}"><jstl:out value="${msgFloats}" /></a>
	<br />
	
	<jstl:if test="${not empty inceptionRecord or emptyPeriodRecords eq false or emptyLinkRecords eq false or emptyMiscellaneousRecords eq false or emptyLegalRecords eq false}">
	<h3><jstl:out value="${brotherhoodHistory}"></jstl:out></h3>
	</jstl:if>
	
	<jstl:if test="${not empty inceptionRecord}">
		<spring:url var="inceptionRecordUrl" value="inceptionRecord/display.do">
			<spring:param name="varId" value="${inceptionRecord.id}"/>
		</spring:url>
		<a href="${inceptionRecordUrl}"><jstl:out value="${inceptionRecords}" /></a>
	<br />
	</jstl:if>
	
	
    <jstl:if test="${emptyPeriodRecords eq false}">
        <spring:url var="periodRecordUrl" value="periodRecord/list.do">
			<spring:param name="varId" value="${brotherhood.id}"/>
		</spring:url>
		<a href="${periodRecordUrl}"><jstl:out value="${periodRecords}" /></a>
	<br />
    </jstl:if>
	<jstl:if test="${emptyLinkRecords eq false}">
		<spring:url var="linkRecordUrl" value="linkRecord/list.do">
			<spring:param name="varId" value="${brotherhood.id}"/>
		</spring:url>
		<a href="${linkRecordUrl}"><jstl:out value="${linkRecords}" /></a>
	<br />
	</jstl:if>
	
	<jstl:if test="${emptyMiscellaneousRecords eq false}">
		<spring:url var="miscellaneousRecordUrl" value="miscellaneousRecord/list.do">
			<spring:param name="varId" value="${brotherhood.id}"/>
		</spring:url>
		<a href="${miscellaneousRecordUrl}"><jstl:out value="${miscellaneousRecords}" /></a>
	<br />
	</jstl:if>
	
	<jstl:if test="${emptyLegalRecords eq false}">
		<spring:url var="legalRecordUrl" value="legalRecord/list.do">
			<spring:param name="varId" value="${brotherhood.id}"/>
		</spring:url>
		<a href="${legalRecordUrl}"><jstl:out value="${legalRecords}" /></a>
	<br />
	</jstl:if>
	<br />