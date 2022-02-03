package br.edu.ufcg.virtus.business.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepresentativeDTO extends ModelDTO{
	
	private Integer id;
	
	private String office;

	private Integer peopleId;

	private Boolean signatory;

	private Boolean companyRepresentative;

	private Boolean technicalRepresentative;

}
