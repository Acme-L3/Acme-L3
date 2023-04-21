<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="authenticated.offer.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="authenticated.offer.list.label.heading" path="heading" width="30%"/>
	<acme:list-column code="authenticated.offer.list.label.summary" path="summary" width="50%"/>
</acme:list>