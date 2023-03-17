
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	protected String			name;

	@Column(unique = true)
	@NotBlank
	@Length(max = 25)
	protected String			VATnumber;

	@NotBlank
	@Length(max = 100)
	protected String			summary;

	@URL
	protected String			link;
}
