<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textbox code="lecturer.lecture.form.label.abstractText" path="abstractText"/>
	<acme:input-textbox code="lecturer.lecture.form.label.body" path="body"/>
	<acme:input-select code="lecturer.lecture.form.label.lectureType" path="lectureType" choices="${types}"/>
	<acme:input-double code="lecturer.lecture.form.label.estimateLearningTime" path="estimateLearningTime"/>
	<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false}">
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update?courseId=${courseId}"/>
			<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete?courseId=${courseId}"/>
			<acme:submit code="lecturer.lecture.form.button.publish" action="/lecturer/lecture/publish?courseId=${courseId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>