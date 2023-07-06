<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>
	<acme:input-textbox code="student.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="student.course.form.label.abstractText" path="abstractText"/>
	<acme:input-textbox code="student.course.form.label.link" path="link"/>
	<acme:input-textbox code="student.course.form.label.lecturers" path="lecturers"/>
	
	<acme:button code="student.course.form.button.lecturer" action="/student/lecture/list?id=${id}"/>
	
</acme:form>