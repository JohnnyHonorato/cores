package br.edu.ufcg.virtus.core.dto;

import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.domain.OpenMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleDTO extends ModelDTO {

    private Integer id;
    private String icon;
    private String name;
    private String link;
    private OpenMode openMode;
    private String pathName;
    private boolean visible;
    private Set<PermissionDTO> permissions;
}
