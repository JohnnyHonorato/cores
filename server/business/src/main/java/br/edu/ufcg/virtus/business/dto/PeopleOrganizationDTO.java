package br.edu.ufcg.virtus.business.dto;

import br.edu.ufcg.virtus.business.dto.simple.PeopleSimpleDTO;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleOrganizationDTO extends ModelDTO {

    private Integer id;
    private String department;
    private String position;
    private PeopleSimpleDTO people;

}
