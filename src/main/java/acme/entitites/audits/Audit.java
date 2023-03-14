
package acme.entitites.audits;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.datatypes.Mark;
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
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strong_points;

	@NotBlank
	@Length(max = 100)
	protected String			weak_points;

	// Derived attributes -----------------------------------------------------


	@NotNull
	@Transient
	public Mark mostCommonMark() {

		final Map<Mark, Integer> marksCount = new HashMap<>();

		for (final AuditingRecord auditingRecord : this.auditingRecords) {
			final Mark mark = auditingRecord.getMark();
			marksCount.put(mark, marksCount.getOrDefault(mark, 0) + 1);
		}

		final int maxCount = Collections.max(marksCount.values());

		final List<Mark> mostCommonMarks = marksCount.entrySet().stream().filter(entry -> entry.getValue() == maxCount).map(Map.Entry::getKey).collect(Collectors.toList());

		if (mostCommonMarks.size() == 1)
			return mostCommonMarks.get(0);
		else {
			final int randomIndex = new Random().nextInt(mostCommonMarks.size());
			return mostCommonMarks.get(randomIndex);
		}
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Auditor				auditor;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "audit")
	protected List<AuditingRecord>	auditingRecords;

	//TODO: relationship with Course

}
