<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
   	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
    <acme:input-textbox code="student.activity.form.label.summary" path="summary"/>
    <acme:input-textbox code="student.activity.form.label.activityType" path="activityType"/>
	<acme:input-textbox code="student.activity.form.label.startDate" path="startDate"/>
    <acme:input-textbox code="student.activity.form.label.endDate" path="endDate"/>
    <acme:input-textbox code="student.activity.form.label.link" path="link"/>
		
	<jstl:choose>
<%-- 		<jstl:when test="${acme:anyOf(_command, 'show')}"> --%>
<%-- 			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/> --%>
<%-- 			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/> --%>
<%-- 		</jstl:when> --%>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit test="${_command == 'create'}" code="student.activity.form.button.create" action="/student/activity/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>


