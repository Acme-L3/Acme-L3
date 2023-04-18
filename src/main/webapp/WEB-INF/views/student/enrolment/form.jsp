<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	
	<acme:button code="student.enrolment.form.button.activity" action="/student/activity/list?enrolmentId=${id}"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show')}">
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:submit test="${_command == 'create'}" code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>



