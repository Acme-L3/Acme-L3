
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		countOfWorkbookActivities;

	Double						averageActivity;
	Double						minActivity;
	Double						maxActivity;
	Double						desvActivity;

	Double						averageCourse;
	Double						minCourse;
	Double						maxCourse;
	Double						desvCourse;

	// Relationships ----------------------------------------------------------

}
