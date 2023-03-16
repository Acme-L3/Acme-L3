
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<String, Integer>		countOfRole;

	Map<String, Double>			ratioOfThings;

	Map<String, Double>			averageBudgetInOffersByCurrency;
	Map<String, Double>			deviationBudgetInOffersByCurrency;
	Map<String, Double>			minBudgetInOffersByCurrency;
	Map<String, Double>			maxBudgetInOffersByCurrency;

	Double						averageNumberOfNotesInLastTenWeeks;
	Double						deviationNumberOfNotesInLastTenWeeks;
	Double						minNumberOfNotesInLastTenWeeks;
	Double						maxNumberOfNotesInLastTenWeeks;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
