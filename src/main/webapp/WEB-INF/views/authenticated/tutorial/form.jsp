<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.form.code" path="code"/>
	<acme:input-textbox code="authenticated.tutorial.form.tittle" path="tittle"/>
	<acme:input-textbox code="authenticated.tutorial.form.summary" path="summary"/>
	<acme:input-textbox code="authenticated.tutorial.form.goals" path="goals"/>
	<acme:input-double code="authenticated.tutorial.form.estimated-time" path="estimatedTime"/>
	<acme:input-select code="authenticated.tutorial.form.course" path="course" choices ="${courses}"/>
	
	<label>
			<acme:message code="authenticated.tutorial.form.message"/>
	</label>
	
	<acme:input-textbox code="authenticated.tutorial.form.assistant.supervisor" path="assistant.supervisor"/>
	<acme:input-textbox code="authenticated.tutorial.form.assistant.curriculum" path="assistant.curriculum"/>
	<acme:input-textbox code="authenticated.tutorial.form.assistant.expertiseField" path="assistant.expertiseField"/>
	<acme:input-textbox code="authenticated.tutorial.form.assistant.link" path="assistant.link"/>
	
</acme:form>