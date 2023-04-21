<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<div class="rounded" style="background: <acme:message code='master.banner.background'/>">
	<img src="images/banner.png" alt="<acme:message code='master.banner.alt'/>" class="img-fluid rounded"/>
	<a href="${banner.linkDocument}">
		<img src="${banner.linkPhoto}" alt="${banner.slogan}" class="img-fluid rounded" style="width: 10%; height: 10%;"/>
	</a>
</div>
