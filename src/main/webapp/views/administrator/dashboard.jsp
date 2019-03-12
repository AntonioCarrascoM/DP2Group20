<%--
 * dashboard.jsp
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

<spring:message code="administrator.querycol" var="querycol" />
<spring:message code="administrator.valuecol" var="valuecol" />
<spring:message code="administrator.queryc1" var="queryc1" />
<spring:message code="administrator.queryc2" var="queryc2" />
<spring:message code="administrator.queryc3" var="queryc3" />
<spring:message code="administrator.queryc4" var="queryc4" />
<spring:message code="administrator.queryc5" var="queryc5" />
<spring:message code="administrator.queryc6" var="queryc6" />
<spring:message code="administrator.queryc7" var="queryc7" />
<spring:message code="administrator.queryc8" var="queryc8" />
<spring:message code="administrator.queryb11" var="queryb11" />
<spring:message code="administrator.queryb12" var="queryb12" />
<spring:message code="administrator.queryb13" var="queryb13" />
<spring:message code="administrator.queryb2" var="queryb2" />
<spring:message code="administrator.queryb3" var="queryb3" />
<spring:message code="administrator.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')" >

	<%-- Displays the result of all required database queries --%>
	
	<table style="width:100%">
 		
  		<tr>
    		<td><jstl:out value="${queryc1}" /></td>
    		<td><jstl:out value="${minMaxAvgStddevMemberPerBrotherhood}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc2}" /></td>
    		<td><jstl:out value="${largestBrotherhoods}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc3}" /></td>
    		<td><jstl:out value="${smallestBrotherhoods}" /></td> 
  		</tr>

  		<tr>
    		<td><jstl:out value="${queryc4}" /></td>
    		<td><jstl:out value="${ratioRequestsByStatus}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc5}" /></td>
    		<td><jstl:out value="${paradesBefore30Days}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc6}" /></td>
    		<td><jstl:out value="${ratioRequestsByStatus}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc7}" /></td>
    		<td><jstl:out value="${membersWithApprovedRequestsThanAvg}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc8}" />
    		<jstl:out value="${histogramOfPositions1}" />
    		<td><jstl:out value="${histogramOfPositions2}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryb11}" /></td>
    		<td><jstl:out value="${ratioOfBrotherhoodsByArea}" /></td> 
  		</tr>
  		<tr>
    		<td><jstl:out value="${queryb12}" /></td>
    		<td><jstl:out value="${countOfBrotherhoodsByArea}" /></td> 
  		</tr>
  		<tr>
    		<td><jstl:out value="${queryb13}" /></td>
    		<td><jstl:out value="${minMaxAvgAndStddevOfBrotherhoodsByArea}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryb2}" /></td>
    		<td><jstl:out value="${minMaxAvgStddevResultsFinders}" /></td> 
  		</tr>
  		<tr>
    		<td><jstl:out value="${queryb3}" /></td>
    		<td><jstl:out value="${ratioEmptyVersusNonEmptyFinders}" /></td> 
  		</tr>
  		
	</table>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>