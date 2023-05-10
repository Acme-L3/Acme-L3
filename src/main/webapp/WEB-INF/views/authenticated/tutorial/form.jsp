<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.form.code" path="code"/>
	<acme:input-textbox code="authenticated.tutorial.form.tittle" path="tittle"/>
	<acme:input-textbox code="authenticated.tutorial.form.summary" path="summary"/>
	<acme:input-textbox code="authenticated.tutorial.form.goals" path="goals"/>
	<acme:input-textbox code="authenticated.tutorial.form.startDate" path="startDate"/>
	<acme:input-textbox code="authenticated.tutorial.form.endDate" path="endDate"/>
	<acme:input-select code="authenticated.tutorial.form.assistant" path="assistant" choices ="${assistants}"/>
	<acme:input-select code="authenticated.tutorial.form.course" path="course" choices ="${courses}"/>
</acme:form>