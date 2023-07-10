<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textbox code="any.course.form.label.abstractText" path="abstractText"/>
	<acme:input-money code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="any.course.form.label.courseType" path="courseType"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>

</acme:form>

	<acme:button code="authenticated.course.list.button.list.tutorials" action="/authenticated/tutorial/list?masterId=${id}"/>
	<acme:button code="authenticated.course.list.button.list.audits" action="/authenticated/audit/list?masterId=${id}"/>	
	<acme:button code="authenticated.course.list.button.list.practicums" action="/authenticated/practicum/list?masterId=${id}"/>	