
package br.edu.ufcg.virtus.core.dto;

import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO extends ModelDTO {

    private Integer id;
    private String name;
    private Set<PermissionDTO> permissions;

}
