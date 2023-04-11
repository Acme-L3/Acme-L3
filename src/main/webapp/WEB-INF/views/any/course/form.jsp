<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textbox code="any.course.form.label.cAbstract" path="cAbstract"/>
	<acme:input-textbox code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-textbox code="any.course.form.label.furtherInfo" path="furtherInfo"/>
	
</acme:form>