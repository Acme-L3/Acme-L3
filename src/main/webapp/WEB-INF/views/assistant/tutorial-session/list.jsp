<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorialSession.list.tittle" path="tittle" width="20%"/>	
	<acme:list-column code="assistant.tutorialSession.list.summary" path="summary" width="80%"/>
</acme:list>

<acme:button test="${showCreate}" code="assistant.tutorialSession.list.button.create" action="/assistant/tutorial-session/create?tutorialId=${tutorialId}"/>