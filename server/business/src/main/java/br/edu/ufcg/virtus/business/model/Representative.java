package br.edu.ufcg.virtus.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "representative")
@SQLDelete(sql = "UPDATE representative SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@NoArgsConstructor
@Getter
@Setter
public class Representative extends Model<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull(message = "{people.organizations.people.required}")
	@Column(name = "people_id")
	private Integer peopleId;
	
	@Column(name = "office")
	private Integer office;

	@Column(name = "signatory", columnDefinition = "boolean default false")
	private Boolean signatory;

	@Column(name = "company_representative", columnDefinition = "boolean default false")
	private Boolean companyRepresentative;

	@Column(name = "technical_representative", columnDefinition = "boolean default false")
	private Boolean technicalRepresentative;

	@ManyToOne
	@JoinColumn(name = "business_opportunity_id", referencedColumnName = "id")
	private BusinessOpportunity businessOpportunity;

	@Column(name = "deleted")
	private boolean deleted;

}
