package br.edu.ufcg.virtus.business.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.business.dto.simple.RoleSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String username;
    private RoleSimpleDTO role;
    private boolean external;

}
