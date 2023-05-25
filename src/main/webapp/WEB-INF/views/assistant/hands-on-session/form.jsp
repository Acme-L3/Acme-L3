<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.practica.form.tittle" path="tittle"/>
	<acme:input-textbox code="assistant.practica.form.summary" path="summary"/>
	<acme:input-moment code="assistant.practica.form.creationMoment" path="creationMoment" readonly="true"/>
	<acme:input-moment code="assistant.practica.form.startDate" path="startDate"/>
	<acme:input-moment code="assistant.practica.form.endDate" path="endDate"/>
	<acme:input-url code="assistant.practica.form.link" path="link"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.practica.form.button.update" action="/assistant/hands-on-session/update"/>
			<acme:submit code="assistant.practica.form.button.delete" action="/assistant/hands-on-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.practica.form.button.create" action="/assistant/hands-on-session/create?tutorialId=${tutorialId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>