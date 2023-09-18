<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>

	<acme:list-column code="authenticated.student.list.label.statement" path="statement"/>
	<acme:list-column code="authenticated.student.list.label.strongFeatures" path="strongFeatures"/>
	<acme:list-column code="authenticated.student.list.label.weakFeatures" path="weakFeatures"/>
	<acme:list-column code="authenticated.student.list.label.link" path="link"/>

</acme:list>