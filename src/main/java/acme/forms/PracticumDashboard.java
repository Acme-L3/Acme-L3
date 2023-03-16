
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PracticumDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	Map<Integer, Integer>		totalPracticaByMonth;

	double						averageSessionLength;

	double						deviationSessionLength;

	double						minimumSessionLength;

	double						maximumSessionLength;

}
