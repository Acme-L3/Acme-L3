<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="auditor.audit.form.code" path="code"/>
	<acme:input-textbox code="auditor.audit.form.conclusion" path="conclusion"/>	
	<acme:input-textbox code="auditor.audit.form.strongPoints" path="strongPoints"/>
	<acme:input-textbox code="auditor.audit.form.weakPoints" path="weakPoints"/>
	<acme:input-select code="auditor.audit.form.course" path="course" choices ="${courses}"/>
	<acme:input-checkbox code="auditor.audit.form.draftMode" path="draftMode"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.audit.form.button.auditingRecords" action="/auditor/auditingRecord/list?auditId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.audit.form.button.auditingRecords" action="/auditor/auditingRecord/list?auditId=${id}"/>
			<acme:submit code="auditor.audit.form.button.update" action="/auditor/audit/update"/>
			<acme:submit code="employer.audit.form.button.delete" action="/auditor/audit/delete"/>
			<acme:submit code="employer.audit.form.button.publish" action="/auditor/audit/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.audit.form.button.create" action="/auditor/audit/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>