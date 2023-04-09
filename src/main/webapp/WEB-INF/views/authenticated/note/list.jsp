<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.note.list.moment" path="moment"/>
	<acme:list-column code="authenticated.note.list.title" path="title"/>
	<acme:list-column code="authenticated.note.list.author" path="author"/>
	<acme:list-column code="authenticated.note.list.message" path="message"/>
	<acme:list-column code="authenticated.note.list.email" path="email"/>
	<acme:list-column code="authenticated.note.list.link" path="link"/>
</acme:list>

<acme:button code="authenticated.note.list.post" action="/authenticated/note/create"/>