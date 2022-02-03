package br.edu.ufcg.virtus.business.dto.simple;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSimpleDTO extends ModelDTO {

    private Integer id;
    private String name;

}
