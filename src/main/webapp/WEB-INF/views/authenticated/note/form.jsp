<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="authenticated.note.form.moment" path="moment" readonly="true"/>
	<acme:input-textbox code="authenticated.note.form.author" path="author" readonly="true"/>
	<acme:input-textbox code="authenticated.note.form.title" path="title"/>
	<acme:input-textarea code="authenticated.note.form.message" path="message"/>
	<acme:input-textbox code="authenticated.note.form.email" path="email"/>
	<acme:input-url code="Link" path="link"/>
	
	<jstl:if test="${ _command == 'create'}">
		<acme:input-checkbox code="authenticated.note.form.confirm" path="confirmation"/>
		<acme:submit code="authenticated.note.form.post" action="/authenticated/note/create"/>
	</jstl:if>
</acme:form>