<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="administrator.banner.form.initMoment" path="initMoment" readonly="true"/>
	<acme:input-moment code="administrator.banner.form.endMoment" path="endMoment" readonly="true"/>	
	<acme:input-textbox code="administrator.banner.form.slogan" path="slogan"/>
	<acme:input-moment code="administrator.banner.form.startDate" path="startDate"/>
	<acme:input-moment code="administrator.banner.form.endDate" path="endDate"/>
	<acme:input-url code="administrator.banner.form.linkPhoto" path="linkPhoto"/>
	<acme:input-url code="administrator.banner.form.linkDocument" path="linkDocument"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
			<acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>