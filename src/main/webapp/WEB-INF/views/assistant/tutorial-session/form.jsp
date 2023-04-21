<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialSession.form.tittle" path="tittle"/>
	<acme:input-textbox code="assistant.tutorialSession.form.summary" path="summary"/>
	<acme:input-moment code="assistant.tutorialSession.form.creationMoment" path="creationMoment" />
	<acme:input-moment code="assistant.tutorialSession.form.startDate" path="startDate"/>
	<acme:input-moment code="assistant.tutorialSession.form.endDate" path="endDate"/>
	<acme:input-url code="assistant.tutorialSession.form.link" path="link"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorialSession.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorialSession.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorialSession.form.button.create" action="/assistant/tutorial-session/create?tutorialId=${tutorialId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>