package br.edu.ufcg.virtus.core.dto;

import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.dto.simple.RoleSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String username;
    private Set<RoleSimpleDTO> roles;
    private boolean external;

}
