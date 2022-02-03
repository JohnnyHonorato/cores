package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.simple.TrackerModelSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String color;
    private TrackerModelSimpleDTO trackerModel;
}
