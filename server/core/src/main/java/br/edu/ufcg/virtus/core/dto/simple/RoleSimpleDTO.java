package br.edu.ufcg.virtus.core.dto.simple;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleSimpleDTO extends ModelDTO {

    private Integer id;
    private String name;

}
