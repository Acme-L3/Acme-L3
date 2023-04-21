<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${acme:anyOf(_command, 'show')}">
	<acme:input-textbox code="administrator.system-configuration.form.systemCurrency" path="systemCurrency"/>
	<acme:input-textbox code="administrator.system-configuration.form.acceptedCurrencies" path="acceptedCurrencies"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show')}">
			<acme:button code="administrator.system-configuration.form.button.update" action="/administrator/system-configuration/update"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'update')}">
			<acme:submit code="administrator.system-configuration.form.button.update" action="/administrator/system-configuration/update"/>
		</jstl:when>
	</jstl:choose>
</acme:form>