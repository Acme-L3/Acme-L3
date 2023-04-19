<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="auditor.auditingRecord.form.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditingRecord.form.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditingRecord.form.initialMoment" path="initialMoment" />
	<acme:input-moment code="auditor.auditingRecord.form.finalMoment" path="finalMoment"/>
	<acme:input-textbox code="auditor.auditingRecord.form.mark" path="mark"/>
	<acme:input-url code="auditor.auditingRecord.form.link" path="link"/>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditingRecord.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditingRecord.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create?auditId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>