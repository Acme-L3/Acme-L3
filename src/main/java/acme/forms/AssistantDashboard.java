
package acme.forms;

import java.util.Map;

import acme.datatypes.Statistics;
import acme.entitites.session.SessionType;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected Map<SessionType, Integer>	tutorialCount;

	//Statistics of tutorial

	protected Statistics				tutorialStatistics;

	//Statistics of sessions

	protected Statistics				sessionStatistics;

	// Relationships ----------------------------------------------------------

}
