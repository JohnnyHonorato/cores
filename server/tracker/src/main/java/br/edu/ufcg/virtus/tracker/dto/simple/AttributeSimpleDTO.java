package br.edu.ufcg.virtus.tracker.dto.simple;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeSimpleDTO extends ModelDTO {

    private Integer id;
    private String title;
    private Boolean showOnCard;
    private String currency;
    private AttributeType type;

}
