
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		countOfAuditsLectures;

	Double						totalNumberAudits;
	Double						averageAuditingRecords;
	Double						averageTimeAuditingRecords;
	Double						deviationAuditingRecords;
	Double						timeDeviationAuditingRecords;
	Double						minAuditingRecords;
	Double						maxAuditingRecords;
	Double						minTimeAuditingRecords;
	Double						maxTimeAuditingRecords;

	// Relationships ----------------------------------------------------------

}
