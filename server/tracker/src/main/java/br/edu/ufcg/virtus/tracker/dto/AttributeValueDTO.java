package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.simple.AttributeSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValueDTO extends ModelDTO {

    private Integer id;
    private String value;
    private String valueComplement;
    private AttributeSimpleDTO attribute;

}
