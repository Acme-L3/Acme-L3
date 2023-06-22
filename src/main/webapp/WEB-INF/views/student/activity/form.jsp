<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
   	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
    <acme:input-textarea code="student.activity.form.label.summary" path="summary"/>
    <acme:input-select code="student.activity.form.label.activityType" path="activityType" choices="${activities}"/>
	<acme:input-moment code="student.activity.form.label.initDate" path="initDate"/>
    <acme:input-moment code="student.activity.form.label.endDate" path="endDate"/>
    <acme:input-textbox code="student.activity.form.label.link" path="link"/>
        
		
	<jstl:choose>
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete')}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit test="${_command == 'create'}" code="student.activity.form.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>


