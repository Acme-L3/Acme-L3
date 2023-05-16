<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.code" path="code" width="25%"/>
	<acme:list-column code="auditor.audit.list.conclusion" path="conclusion" width="25%"/>
	<acme:list-column code="auditor.audit.list.strongPoints" path="strongPoints" width="25%"/>

</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="auditor.audit.list.button.create" action="/auditor/audit/create"/>
</jstl:if>	