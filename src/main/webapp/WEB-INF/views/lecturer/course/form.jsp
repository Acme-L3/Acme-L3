<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form readonly="${published}">
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|create|update|delete|publish')}">
			<acme:input-textbox code = "lecturer.course.form.label.code" path="code"/>
			<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>	
			<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
			<acme:input-textbox code="lecturer.course.form.label.abstractText" path="abstractText"/>
			<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
				<acme:input-select code="lecturer.course.form.label.courseType" path="courseType" choices="${types}" readonly="true"/>
			</jstl:if>
			<acme:input-url code="lecturer.course.form.label.link" path="link"/>
			
			<jstl:choose>	 
				<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isPublished == false}">
					<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update?id=${id}"/>
					<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete?id=${id}"/>
					<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish?id=${id}"/>
					<acme:button code="lecturer.course.form.button.add-lecture" action="/lecturer/course/add-lecture?id=${id}"/>
					<acme:button code="lecturer.course.form.button.remove-lecture" action="/lecturer/course/remove-lecture?id=${id}"/>
					<acme:button code="lecturer.course.form.button.lecture" action="/lecturer/lecture/list?courseId=${id}"/>
				</jstl:when>
				<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
					<acme:button code="lecturer.course.form.button.lecture" action="/lecturer/lecture/list?courseId=${id}"/>
				</jstl:when>
				<jstl:when test="${_command == 'create'}">
					<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'add-lecture|remove-lecture')}">
			<acme:input-textbox code="lecturer.course.form.label.code" path="code" readonly="true"/>
			<acme:input-textbox code="lecturer.course.form.label.title" path="title" readonly="true"/>
			<acme:input-select code="lecturer.course.form.label.lecture" path="lecture" choices="${lectures}"/>
			<jstl:choose>	 
				<jstl:when test="${_command == 'add-lecture'}">
					<acme:submit code="lecturer.course.form.button.add-lecture" action="/lecturer/course/add-lecture"/>
				</jstl:when>
				<jstl:when test="${_command == 'remove-lecture'}">
					<acme:submit code="lecturer.course.form.button.remove-lecture" action="/lecturer/course/remove-lecture"/>
				</jstl:when>		
			</jstl:choose>
		</jstl:when>
	</jstl:choose>
</acme:form>