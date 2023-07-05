<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="auditor.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-theory-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTheoryAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-handson-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfHandsOnAudits}"/>
		</td>
	</tr>
	<tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.total-number-of-balanced-audits"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfBalancedAudits}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.average-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviation-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${deviationNumberOfAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${maximumNumberOfAuditingRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimum-number-of-auditing-records"/>
		</th>
		<td>
			<acme:print value="${minimumNumberOfAuditingRecords}"/>
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
