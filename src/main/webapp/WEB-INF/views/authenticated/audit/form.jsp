<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.audit.form.code" path="code"/>
	<acme:input-textbox code="authenticated.audit.form.conclusion" path="conclusion"/>
	<acme:input-textbox code="authenticated.audit.form.strongPoints" path="strongPoints"/>
	<acme:input-textbox code="authenticated.audit.form.weakPoints" path="weakPoints"/>
	<acme:input-select code="authenticated.audit.form.auditor" path="auditor" choices ="${auditors}"/>
	<acme:input-select code="authenticated.audit.form.course" path="course" choices ="${courses}"/>
</acme:form>