package br.edu.ufcg.virtus.tracker.dto;

import java.util.Calendar;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeDTO extends ModelDTO {

    private Integer id;

    private String title;

    private AttributeType type;

    private Boolean required;

    private Integer position;
    
    private Integer positionX;
    
    private Integer positionY;
    
    private Integer numberOfColumns;

    private Double minValue;

    private Double maxValue;

    private Integer maxLength;

    private String listValues;
    
    private Boolean showOnCard;

    private boolean needsValueComplement;
    
    private String currency;

    private Calendar minDate;

    private Calendar maxDate;
    
    private boolean deleted;
    
    private AttributeDTO relatedAttribute;
}
