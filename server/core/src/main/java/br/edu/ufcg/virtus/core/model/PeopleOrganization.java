package br.edu.ufcg.virtus.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "people_organization")
@Getter
@Setter
public class PeopleOrganization extends Model<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "department")
	private String department;

	@Column(name = "position")
	private String position;

	@Column(name = "technical_manager", columnDefinition = "boolean default false")
	private Boolean technicalManager;

	@ManyToOne
	@JoinColumn(name = "people_id", referencedColumnName = "id")
	@NotNull(message = "{people.organizations.people.required}")
	private People people;

	@ManyToOne
	@JoinColumn(name = "organization_id", referencedColumnName = "id")
	@NotNull(message = "{people.organizations.organization.required}")
	private Organization organization;

}
