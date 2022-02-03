package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssigneeDTO extends ModelDTO {

    private Integer id;
    private Integer peopleId;
    private String name;

}
