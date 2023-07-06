<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.practicum-session.form.label.title" path="title"/>
	<acme:input-textbox code="company.practicum-session.form.label.summary" path="summary"/>
	<acme:input-moment code="company.practicum-session.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="company.practicum-session.form.label.endDate" path="endDate"/>
	<acme:input-textbox code="company.practicum-session.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${(_command == 'show' || _command == 'update') && draftMode}">
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum-session/delete"/>
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum-session/update"/>
		</jstl:when>		
		<jstl:when test="${_command == 'create' && draftMode}">
			<acme:submit code="company.practicum-session.form.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'addendum' && !draftMode}">
			<acme:input-checkbox code="company.practicum-session.form.button.check" path="check"/>
			<acme:submit code="company.practicum-session.form.button.addendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>