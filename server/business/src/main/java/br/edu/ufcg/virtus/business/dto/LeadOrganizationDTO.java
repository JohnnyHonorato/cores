package br.edu.ufcg.virtus.business.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.business.dto.simple.OrganizationSimpleDTO;
import br.edu.ufcg.virtus.business.dto.simple.LeadSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadOrganizationDTO extends ModelDTO {

    private Integer id;
    private String department;
    private String position;
    private LeadSimpleDTO people;
    private OrganizationSimpleDTO organization;

}
