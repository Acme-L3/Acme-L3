<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>

	<jstl:choose>
	 	
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'delete'||_command == 'publish') && draftMode == true}">
			<acme:input-textbox code="student.enrolment.form.label.course" path="coursesRead" readonly="true"/>
			<acme:input-textbox code="student.enrolment.form.label.holderName" path="holderName"/>
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble" path="lowerNibble"/>
			<acme:input-textbox code="student.enrolment.form.label.expiryDate" path="expiryDate"/>
 			<acme:input-textbox code="student.enrolment.form.label.cvc" path="cvc"/>
			<acme:submit code="student.enrolment.form.button.publish" action="/student/enrolment/publish"/>
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'show' && draftMode == false}">
		<acme:input-textbox code="student.enrolment.form.label.course" path="coursesRead" readonly="true"/>
		<acme:button code="student.activity.form.button.activities" action="/student/activity/list?enrolmentId=${id}"/>
		</jstl:when>
    
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>