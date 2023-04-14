<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="administrator.banner.list.startDate" path="startDate" width="20%"/>
	<acme:list-column code="administrator.banner.list.endDate" path="endDate" width="20%"/>	
	<acme:list-column code="administrator.banner.list.slogan" path="slogan" width="60%"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>