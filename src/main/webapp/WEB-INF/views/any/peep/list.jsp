<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>




<acme:list>
	<acme:list-column code="any.peep.list.label.title" path="title" width="20%"/>
	<acme:list-column code="any.peep.list.label.nick" path="nick" width="20%"/>
	<acme:list-column code="any.peep.list.label.message" path="message" width="60%"/>
</acme:list>


<acme:button code="any.peep.list.button.create" action="/any/peep/create"/>
