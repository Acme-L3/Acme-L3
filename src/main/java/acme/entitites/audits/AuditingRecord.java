
package acme.entitites.audits;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			subject;

	@NotBlank
	@Length(max = 100)
	protected String			assessment;

	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				initialMoment;

	//Custom restriction 1 hour duration
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				finalMoment;

	@NotBlank
	@Pattern(regexp = "^((A_PLUS|A|B|C|F|F_MINUS))$")
	protected String			mark;

	@URL
	protected String			link;

	@NotNull
	protected boolean			correction;

	@NotNull
	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;


	@Transient
	public Double getHoursFromPeriod() {
		final Duration duration = MomentHelper.computeDuration(this.initialMoment, this.finalMoment);
		return duration.getSeconds() / 3600.0;
	}

}
