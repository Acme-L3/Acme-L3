<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.abstractText" path="abstractText" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.body" path="body" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureType" path="lectureType" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.estimateLearningTime" path="estimateLearningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.link" path="link" width="20%"/>
</acme:list>

<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>