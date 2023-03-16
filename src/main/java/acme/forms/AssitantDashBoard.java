
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssitantDashBoard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		totalSessions;

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
