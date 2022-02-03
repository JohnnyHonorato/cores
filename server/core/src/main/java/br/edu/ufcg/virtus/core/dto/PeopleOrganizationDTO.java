package br.edu.ufcg.virtus.core.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.dto.simple.OrganizationSimpleDTO;
import br.edu.ufcg.virtus.core.dto.simple.PeopleSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleOrganizationDTO extends ModelDTO {

	private Integer id;
	private String department;
	private String position;
	private PeopleSimpleDTO people;
	private OrganizationSimpleDTO organization;
	private Boolean technicalManager;

}
