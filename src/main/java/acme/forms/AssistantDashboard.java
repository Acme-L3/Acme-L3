
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						totalNumberOfTutorials;

	Double						averageTimePerSession;
	Double						deviationTimeSession;
	Double						minTimeSession;
	Double						maxTimeSession;
	Double						averageTimePerTutorial;
	Double						deviationTimeTutorial;
	Double						minTimeTutorial;
	Double						maxTimeTutorial;

	// Relationships ----------------------------------------------------------

}
