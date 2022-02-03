package br.edu.ufcg.virtus.business.dto;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessOpportunityDTO extends ModelDTO{
	
	private Integer id;
	
	private String title;
	
	private String description;
	
	private Integer organizationId;
	
	private List<RepresentativeDTO> representatives;

}
