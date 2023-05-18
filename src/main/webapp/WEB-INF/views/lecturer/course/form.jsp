<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form readonly="${published}">
	<acme:input-textbox code = "lecturer.course.form.label.code" path="code"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>	
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="lecturer.course.form.label.abstractText" path="abstractText"/>
	<acme:input-select code="lecturer.course.form.label.courseType" path="courseType" choices="${types}" readonly="true"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && published}">
			<acme:button code="lecturer.course.form.button.lecture" action="/lecturer/lecture/list-by-course?courseId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && published == false}">
			<acme:button code="lecturer.course.form.button.lecture" action="/lecturer/lecture/list-by-course?courseId=${id}"/>
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>