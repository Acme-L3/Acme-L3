<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.practica.list.tittle" path="tittle" width="20%"/>	
	<acme:list-column code="assistant.practica.list.summary" path="summary" width="80%"/>
</acme:list>