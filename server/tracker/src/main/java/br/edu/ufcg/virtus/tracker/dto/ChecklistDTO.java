package br.edu.ufcg.virtus.tracker.dto;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistDTO extends ModelDTO {
    
    private Integer id;
    private String name;
    private List <ChecklistItemDTO> items;

}
