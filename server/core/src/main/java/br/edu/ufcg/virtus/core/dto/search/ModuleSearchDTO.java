package br.edu.ufcg.virtus.core.dto.search;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.domain.OpenMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleSearchDTO extends ModelDTO {

    private Integer id;
    private String name;
    private OpenMode openMode;

}
