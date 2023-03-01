
package acme.entitites.offers;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import acme.framework.components.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer {

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	protected Date		moment;

	@NotBlank
	@Length(max = 75)
	protected String	heading;

	@NotBlank
	@Length(max = 100)
	protected String	summary;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date		startAvailability;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date		endAvailability;

	protected Money		price;

}
