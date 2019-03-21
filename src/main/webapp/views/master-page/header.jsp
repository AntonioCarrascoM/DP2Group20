<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
	<a href="#"><img src="images/logo.png" alt="Acme Parade Co., Inc."
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
					
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/edit.do"><spring:message
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
					<li><a href="administrator/bannableList.do"><spring:message
								code="master.page.administrator.bannableList" /></a></li>

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
					<li><a href="sponsor/create.do"><spring:message
								code="master.page.register.sponsor" /></a></li>
				</ul>
			<li><a class="fNiv" href="brotherhood/list.do"><spring:message
					code="master.page.brotherhood.list" /></a> </li>
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
		
			<li><a class="fNiv"><spring:message code="master.page.member.lists" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a  href="brotherhood/list.do"><spring:message
								code="master.page.brotherhood.list" /></a> </li>
					<li><a href="request/member/list.do"><spring:message
								code="master.page.request.list" /></a></li>
					
					<li><a href="enrolment/member/list.do"><spring:message
								code="master.page.enrolment.list" /></a></li>
					
					<li><a href="enrolment/member/listMyBrotherhoods.do"><spring:message
								code="master.page.member.listMyBrotherhoods" /></a></li>
				</ul>
			<li><a class="fNiv"><spring:message
						code="master.page.member.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/member/edit.do"><spring:message
								code="master.page.member.finderParams" /></a></li>
					<li><a href="finder/member/list.do"><spring:message
								code="master.page.member.consult" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
		<li><a class="fNiv"><spring:message code="master.page.brotherhood.lists" /></a>
				<ul>
				<li class="arrow"></li>
			<li><a href="float/brotherhood/list.do"><spring:message
						code="master.page.float.list" /></a></li>
			<li><a href="parade/brotherhood/list.do"><spring:message
						code="master.page.parade.list" /></a></li>
			<li><a href="member/brotherhood/enrolableList.do"><spring:message
								code="master.page.member.enrolableList" /></a>
					</li>
			<li><a href="enrolment/brotherhood/list.do"><spring:message
								code="master.page.enrolment.brotherhood.list" /></a>
					</li>
		</ul>
		<li><a class="fNiv"><spring:message code="master.page.brotherhood.records" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a  href="inceptionRecord/brotherhood/display.do"><spring:message
								code="master.page.brotherhood.inceptionRecord.list" /></a> </li>
								
					<li><a href="miscellaneousRecord/brotherhood/list.do"><spring:message
								code="master.page.brotherhood.miscellaneousRecord.list" /></a></li>
					
					<li><a  href="legalRecord/brotherhood/list.do"><spring:message
								code="master.page.brotherhood.legalRecord.list" /></a> </li>
					
					<li><a  href="periodRecord/brotherhood/list.do"><spring:message
								code="master.page.brotherhood.periodRecord.list" /></a> </li>
								
					<li><a  href="linkRecord/brotherhood/list.do"><spring:message
								code="master.page.brotherhood.linkRecord.list" /></a> </li>
				</ul>
			<li>
		
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a href="sponsorship/sponsor/list.do"><spring:message
					code="master.page.sponsorship.list" /></a></li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
