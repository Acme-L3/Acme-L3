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
</acme:form>