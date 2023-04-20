<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.title" path="title"/>
	<acme:input-textbox code="lecturer.lecture.form.abstractText" path="abstractText"/>
	<acme:input-textbox code="lecturer.lecture.form.body" path="body"/>
	<acme:input-select code="lecturer.lecture.form.lectureType" path="lectureType" choices="${types}"/>
	<acme:input-double code="lecturer.lecture.form.estimateLearningTime" path="estimateLearningTime"/>
	<acme:input-url code="lecturer.lecture.form.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
			<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>