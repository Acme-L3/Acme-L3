<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditingRecord.list.subject" path="subject" width="80%"/>
	<acme:list-column code="auditor.auditingRecord.list.mark" path="mark"/>		
	<acme:list-column code="auditor.auditingRecord.list.correction" path="correction"/>
</acme:list>

<jstl:choose>
	<jstl:when test="${!showCreate}">
		<acme:button code="auditor.auditingRecord.list.button.createCorrection" action="/auditor/auditing-record/create-correction?auditId=${auditId}"/>
	</jstl:when>
	<jstl:when test="${showCreate}">
		<acme:button code="auditor.auditingRecord.list.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
	</jstl:when>
</jstl:choose>