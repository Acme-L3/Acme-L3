<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.tutorial.list.code" path="code" width="20%"/>
	<acme:list-column code="authenticated.tutorial.list.tittle" path="tittle" width="30%"/>
	<acme:list-column code="authenticated.tutorial.list.summary" path="summary" width="50%"/>
</acme:list>