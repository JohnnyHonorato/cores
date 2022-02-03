package br.edu.ufcg.virtus.core.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO extends ModelDTO {
    
    private Integer id;
    private String name;
    private String label;
    private String description;

}
