
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	protected int				totalNumberOfTheoryAudits;
	protected int				totalNumberOfHandsOnAudits;
	protected int				totalNumberOfBalancedAudits;
	protected Double			averageNumberOfAuditingRecords;
	protected Double			deviationNumberOfAuditingRecords;
	protected Double			minimumNumberOfAuditingRecords;
	protected Double			maximumNumberOfAuditingRecords;
	protected Double			averageTimeAuditingRecords;
	protected Double			timeDeviationAuditingRecords;
	protected Double			minTimeAuditingRecords;
	protected Double			maxTimeAuditingRecords;

	// Relationships ----------------------------------------------------------

}
