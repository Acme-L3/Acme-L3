<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.audit.list.code" path="code"/>
	<acme:list-column code="authenticated.audit.list.conclusion" path="conclusion"/>
</acme:list>