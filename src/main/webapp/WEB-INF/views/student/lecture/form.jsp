<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.lecture.form.label.title" path="title"/>
	<acme:input-textbox code="student.lecture.form.label.abstractText" path="abstractText"/>
	<acme:input-textbox code="student.lecture.form.label.estimateLearningTime" path="estimateLearningTime"/>
	<acme:input-textbox code="student.lecture.form.label.body" path="body"/>
	<acme:input-textbox code="student.lecture.form.label.lectureType" path="lectureType"/>
	<acme:input-textbox code="student.lecture.form.label.link" path="link"/>
</acme:form>