<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="assistant.tutorial.form.code" path="code"/>
	<acme:input-textbox code="assistant.tutorial.form.tittle" path="tittle"/>	
	<acme:input-textbox code="assistant.tutorial.form.summary" path="summary"/>
	<acme:input-textbox code="assistant.tutorial.form.goals" path="goals"/>
	<acme:input-select code="assistant.tutorial.form.course" path="course" choices ="${courses}"/>
	<acme:input-double code="authenticated.tutorial.form.estimated-time" path="estimatedTime" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="assistant.tutorial.form.button.tutorialSessions" action="/assistant/tutorial-session/list?tutorialId=${id}"/>		
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="assistant.tutorial.form.button.tutorialSessions" action="/assistant/tutorial-session/list?tutorialId=${id}"/>
			<acme:submit code="assistant.tutorial.form.button.update" action="/assistant/tutorial/update"/>
			<acme:submit code="assistant.tutorial.form.button.delete" action="/assistant/tutorial/delete"/>
			<acme:submit code="assistant.tutorial.form.button.publish" action="/assistant/tutorial/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial.form.button.create" action="/assistant/tutorial/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>