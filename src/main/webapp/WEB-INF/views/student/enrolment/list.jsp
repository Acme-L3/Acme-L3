<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="student.list.label.code" path="code"/>
	<acme:list-column code="student.list.label.motivation" path="motivation"/>
	<acme:list-column code="student.list.label.goals" path="goals"/>
</acme:list>

<acme:button code="student.enrolment.list.button.create" action="/student/enrolment/create"/>