
package acme.entitites.tutorial;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entitites.course.Course;
import acme.framework.data.AbstractEntity;
import acme.framework.helpers.MomentHelper;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			tittle;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endDate;

	protected boolean			draftMode;

	//Derived attributes --------------------------------------------------------


	@Transient
	public boolean isAvailable() {
		boolean result;
		result = !this.draftMode && MomentHelper.isFuture(this.startDate);
		return result;
	}
	//Relations -----------------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Assistant	assistant;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course	course;
}
