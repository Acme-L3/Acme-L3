<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="administrator.offer.form.moment" path="moment" readonly="true"/>
	<acme:input-textbox code="administrator.offer.form.heading" path="heading"/>
	<acme:input-textbox code="administrator.offer.form.summary" path="summary"/>
	<acme:input-moment code="administrator.offer.form.startAvailability" path="startAvailability"/>
	<acme:input-moment code="administrator.offer.form.endAvailability" path="endAvailability"/>
	<acme:input-money code="administrator.offer.form.price" path="price"/>
	<acme:input-url code="administrator.offer.form.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.offer.form.button.update" action="/administrator/offer/update"/>
			<acme:submit code="administrator.offer.form.button.delete" action="/administrator/offer/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.offer.form.button.create" action="/administrator/offer/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>