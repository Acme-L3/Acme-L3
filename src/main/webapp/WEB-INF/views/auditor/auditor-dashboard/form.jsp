<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${averageAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviation-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${deviationAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${maxAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${minAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${averageTimeAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.time-deviation-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${timeDeviationAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximum-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${maxTimeAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimum-time-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${minTimeAuditingRecords}"/>
		</td>
	</tr>			
</table>

<acme:return/>
