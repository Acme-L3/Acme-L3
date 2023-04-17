<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit.list.code" path="code"/>
	<acme:list-column code="any.audit.list.conclusion" path="conclusion"/>
	<acme:list-column code="any.audit.list.strongPoints" path="strongPoints"/>
	<acme:list-column code="any.audit.list.weakPoints" path="weakPoints"/>
</acme:list>