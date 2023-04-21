<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:message code="lecturer.lecturer-dashboard.form.label.count-title"/>	
	<table class="table table-sm">
		<caption></caption>
		<caption></caption>
		<jstl:forEach items="${countOfLecturesByType}" var="entry"> 	
		<tr>	
			<th id="">	
			<acme:message code="lecturer.lecturer-dashboard.form.label.count-sentence"/>		
			<acme:print value="${entry.key}"/>
			<acme:message code="lecturer.lecturer-dashboard.form.label.colon"/>
			</th>
			<td style= "text-align:right;">				
			<acme:print value="${entry.value}"/>				
			</td>		
		</tr>
		</jstl:forEach>
	</table>
	<acme:message code="lecturer.lecturer-dashboard.form.label.stadistic-lecture-title"/>	
	<table class="table table-sm">
		<caption></caption>
		<caption></caption>
		<jstl:forEach items="${statisticsLearningTimeLectures}" var="entry"> 	
		<tr>	
			<th id="">		
			<acme:print value="${entry.key}"/>
			<acme:message code="lecturer.lecturer-dashboard.form.stadistic-lecture-sentence"/>	
			<acme:message code="lecturer.lecturer-dashboard.form.label.colon"/>
			</th>
			<td style= "text-align:right;">				
			<acme:print value="${entry.value}"/>				
			</td>		
		</tr>
		</jstl:forEach>
	</table>
	<acme:message code="lecturer.lecturer-dashboard.form.label.stadistic-course-title"/>	
	<table class="table table-sm">
		<caption></caption>
		<caption></caption>
		<jstl:forEach items="${statisticsLearningTimeCourses}" var="entry"> 	
		<tr>	
			<th id="">			
			<acme:print value="${entry.key}"/>
			<acme:message code="lecturer.lecturer-dashboard.form.stadistic-course-sentence"/>
			<acme:message code="lecturer.lecturer-dashboard.form.label.colon"/>
			</th>
			<td style= "text-align:right;">				
			<acme:print value="${entry.value}"/>				
			</td>		
		</tr>
		</jstl:forEach>
	</table>
</acme:form>