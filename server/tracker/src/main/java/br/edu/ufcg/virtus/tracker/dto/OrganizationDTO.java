package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String fantasyName;
    private String governmentCode;
    private String description;
    private String image;
    private Boolean deleted;

}
