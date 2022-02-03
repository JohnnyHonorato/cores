package br.edu.ufcg.virtus.core.dto.search;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationSearchDTO extends ModelDTO {

    private Integer id;
    private String fantasyName;
    private String governmentCode;

}
