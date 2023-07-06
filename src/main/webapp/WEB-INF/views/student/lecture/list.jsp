<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list> 
	<acme:list-column code="student.lecture.form.label.title" path="title"/>
	<acme:list-column code="student.lecture.form.label.estimateLearningTime" path="estimateLearningTime"/>
</acme:list>