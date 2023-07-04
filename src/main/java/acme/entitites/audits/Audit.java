
package acme.entitites.audits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entitites.course.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}[0-9]{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;

	protected boolean			draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Mark calculateMark(final Collection<Mark> list) {
		final Map<Mark, Integer> frequencies = new HashMap<>();
		for (final Mark type : list)
			frequencies.put(type, frequencies.getOrDefault(type, 0) + 1);

		final List<Mark> mode = new ArrayList<>();
		int maxFrequencie = 0;
		for (final Map.Entry<Mark, Integer> entry : frequencies.entrySet())
			if (entry.getValue() > maxFrequencie) {
				mode.clear();
				mode.add(entry.getKey());
				maxFrequencie = entry.getValue();
			} else if (entry.getValue() == maxFrequencie)
				mode.add(entry.getKey());

		return mode.get((int) (Math.random() * mode.size()));
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Auditor	auditor;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course	course;

}
