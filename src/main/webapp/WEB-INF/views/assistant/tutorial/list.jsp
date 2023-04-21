<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.tutorial.list.code" path="code" width="25%"/>
	<acme:list-column code="assistant.tutorial.list.tittle" path="tittle" width="25%"/>
	<acme:list-column code="assistant.tutorial.list.summary" path="summary" width="25%"/>	
	<acme:list-column code="assistant.tutorial.list.goals" path="goals" width="25%"/>

</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="assistant.tutorial.list.button.create" action="/assistant/tutorial/create"/>
</jstl:if>