
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

	Double						averageTimePerTutorialSession;
	Double						deviationTimeTutorialSession;
	Double						minTimeTutorialSession;
	Double						maxTimeTutorialSession;
	Double						averageTimePerHandsOnSession;
	Double						deviationTimeHandsOnSession;
	Double						minTimeHandsOnSession;
	Double						maxTimeHandsOnSession;
	Double						averageTimePerTutorial;
	Double						deviationTimeTutorial;
	Double						minTimeTutorial;
	Double						maxTimeTutorial;

	// Relationships ----------------------------------------------------------

}
