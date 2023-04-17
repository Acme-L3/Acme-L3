<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:form>
	<acme:input-moment code="administrator.bulletin.form.moment" path="moment" readonly="true"/>	
	<acme:input-textbox code="administrator.bulletin.form.title" path="title" />
	<acme:input-textbox code="administrator.bulletin.form.message" path="message" />
	<acme:input-checkbox code="administrator.bulletin.form.critical" path="critical" />
	<acme:input-url code="administrator.bulletin.form.link" path="link" />
	
	<jstl:choose> 
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="administrator.bulletin.form.confirm" path="confirmation"/>
			<acme:submit code="administrator.bulletin.form.create" action="/administrator/bulletin/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>