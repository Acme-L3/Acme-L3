<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.assistant.form.supervisor" path="supervisor"/>
	<acme:input-textbox code="authenticated.assistant.form.curriculum" path="curriculum"/>
	<acme:input-textbox code="authenticated.assistant.form.expertiseField" path="expertiseField"/>
	<acme:input-textbox code="authenticated.assistant.form.link" path="link"/>

	<acme:submit test="${_command == 'create'}" code="authenticated.assistant.form.button.create" action="/authenticated/assistant/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.assistant.form.button.update" action="/authenticated/assistant/update"/>	
</acme:form>