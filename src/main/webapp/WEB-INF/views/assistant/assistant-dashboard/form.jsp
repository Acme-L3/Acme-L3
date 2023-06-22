<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.total-number-of-sessions"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-per-session-records"/>
		</th>
		<td>
			<acme:print value="${averageTimePerTutorialSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-of-session-records"/>
		</th>
		<td>
			<acme:print value="${deviationTimeTutorialSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.maximum-time-of-session-records"/>
		</th>
		<td>
			<acme:print value="${maxTimeTutorialSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.minimum-time-of-session-records"/>
		</th>
		<td>
			<acme:print value="${minTimeTutorialSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-per-hands-on-records"/>
		</th>
		<td>
			<acme:print value="${averageTimePerHandsOnSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-of-hands-on-records"/>
		</th>
		<td>
			<acme:print value="${deviationTimeHandsOnSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.maximum-time-of-hands-on-records"/>
		</th>
		<td>
			<acme:print value="${maxTimeHandsOnSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.minimum-time-of-hands-on-records"/>
		</th>
		<td>
			<acme:print value="${minTimeHandsOnSession}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-of-tutorial-records"/>
		</th>
		<td>
			<acme:print value="${averageTimePerTutorial}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.time-deviation-of-tutorial-records"/>
		</th>
		<td>
			<acme:print value="${deviationTimeTutorial}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.maximum-time-of-tutorial-records"/>
		</th>
		<td>
			<acme:print value="${maxTimeTutorial}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.minimum-time-of-tutorial-records"/>
		</th>
		<td>
			<acme:print value="${minTimeTutorial}"/>
		</td>
	</tr>			
</table>

<acme:return/>