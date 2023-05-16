<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditingRecord.list.subject" path="subject" width="60%"/>
	<acme:list-column code="auditor.auditingRecord.list.draft" path="draft" width="40%"/>
</acme:list>

<jstl:choose>
	<jstl:when test="${showCreate==true}">
		<acme:button code="auditor.auditingRecord.list.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
	</jstl:when>
	<jstl:otherwise>
		<acme:button code="auditor.auditingRecord.list.button.correction" action="/auditor/auditing-record/correct?auditId=${auditId}"/>
	</jstl:otherwise>
</jstl:choose>