package br.edu.ufcg.virtus.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "business_opportunity")
@SQLDelete(sql = "UPDATE business_opportunity SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@NoArgsConstructor
@Getter
@Setter
public class BusinessOpportunity extends Model<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@NotNull(message = "{people.organizations.organization.required}")
	@Column(name = "organization_id")
	private Integer organizationId;
	
	@Column(name = "deleted")
    private boolean deleted;

}
