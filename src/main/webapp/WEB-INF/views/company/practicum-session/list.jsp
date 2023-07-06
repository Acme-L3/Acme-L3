<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum-session.list.label.title" path="title"  width="60%"/>
	<acme:list-column code="company.practicum-session.list.label.summary" path="summary" width="20%"/>
	<acme:list-column code="company.practicum-session.list.label.addendum" path="addendum" width="20%"/>
</acme:list>

<jstl:choose>
	<jstl:when test="${ draftMode == true }">
		<acme:button code="company.practicum-session.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
	</jstl:when>
	<jstl:when test="${ draftMode == false && addendumCheck == false }">
		<acme:button code="company.practicum-session.list.button.create.addendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
	</jstl:when>
</jstl:choose>