<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicum-session.form.label.title" path="title"/>
	<acme:input-textbox code="company.practicum-session.form.label.summary" path="summary"/>
	<acme:input-moment code="company.practicum-session.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="company.practicum-session.form.label.endDate" path="endDate"/>
	<acme:input-textbox code="company.practicum-session.form.label.link" path="link"/>
	<acme:input-checkbox code="company.practicum.list.button.check" path="check"/>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum-session/delete"/>
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum-session/update"/>
		</jstl:when>	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'addendum'}">
			<acme:submit code="company.practicum-session.form.button.addendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>