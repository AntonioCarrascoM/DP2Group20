<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme Madruga Co., Inc."
		width="300" height="200" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>

					<security:authorize access="hasRole('BROTHERHOOD')">
						<li><a href="brotherhood/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('MEMBER')">
						<li><a href="member/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>
			
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>

					<li><a href="box/list.do"><spring:message
								code="master.page.box.list" /> </a></li>
					<li><a href="socialProfile/list.do"><spring:message
								code="master.page.socialProfile" /> </a></li>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"> <spring:message
						code="master.page.administrator" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/spammerList.do"><spring:message
								code="master.page.administrator.spammerList" /></a></li>

					<li><a href="administrator/computeScore.do"><spring:message
								code="master.page.administrator.computeScore" /></a></li>

					<li><a href="position/administrator/list.do"><spring:message
								code="master.page.position" /></a></li>

					<li><a href="area/administrator/list.do"><spring:message
								code="master.page.area" /></a></li>

					<li><a href="configuration/administrator/display.do"><spring:message
								code="master.page.configuration" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="member/create.do"><spring:message
								code="master.page.register.member" /></a></li>
					<li><a href="brotherhood/create.do"><spring:message
								code="master.page.register.brotherhood" /></a></li>
				</ul>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.createAcc" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/create.do"><spring:message
								code="master.page.administrator.create" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message
						code="master.page.member.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/member/edit.do"><spring:message
								code="master.page.member.finderParams" /></a></li>
					<li><a href="finder/member/list.do"><spring:message
								code="master.page.member.consult" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message code="master.page.lists" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/member/list.do"><spring:message
								code="master.page.member.list" /></a></li>
					<li><a href="request/member/list.do"><spring:message
								code="master.page.request.list" /></a></li>
				</ul></li>
			
			<li><a class="fNiv"><spring:message
						code="master.page.enrolment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="member/list.do"><spring:message
								code="master.page.createAcc" /></a></li>
					<li><a href="enrolment/member/list.do"><spring:message
								code="master.page.list" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a href="float/brotherhood/list.do"><spring:message
						code="master.page.float.list" /></a></li>
			<li><a href="procession/brotherhood/list.do"><spring:message
						code="master.page.procession.list" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.enrolment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/brotherhood/list.do"><spring:message
								code="master.page.enrolment.list" /></a></li>
				</ul></li>
		</security:authorize>

		<!--  Any actor can see brotherhoods -->
		<li><a class="fNiv" href="brotherhood/list.do"><spring:message
					code="master.page.brotherhood.list" /></a> </li>
					
	    <!--  Any actor can see processions -->
	    
	    <security:authorize access="!isAuthenticated()">
    <!-- if user is not authenticated-->
		<li><a class="fNiv" href="procession/list.do"><spring:message
					code="master.page.procession.list" /></a> </li>
		
		
		<!--  Any actor can see floats -->
		<li><a class="fNiv" href="float/list.do"><spring:message
					code="master.page.float.list" /></a> </li>
					
					
	</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

