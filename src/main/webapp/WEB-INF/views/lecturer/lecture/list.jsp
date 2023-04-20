<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="administrator.offer.list.moment" path="moment" width="30%"/>
	<acme:list-column code="administrator.offer.list.heading" path="heading" width="30%"/>	
	<acme:list-column code="administrator.offer.list.summary" path="summary" width="30%"/>
	<acme:list-column code="administrator.offer.list.startAvailability" path="startAvailability" width="50%"/>
	<acme:list-column code="administrator.offer.list.endAvailability" path="endAvailability" width="50%"/>
	<acme:list-column code="administrator.offer.list.price" path="price" width="50%"/>
	<acme:list-column code="administrator.offer.list.link" path="link" width="50%"/>
</acme:list>

<acme:button code="administrator.offer.list.button.create" action="/administrator/offer/create"/>