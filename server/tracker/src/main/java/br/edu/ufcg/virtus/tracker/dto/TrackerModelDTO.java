package br.edu.ufcg.virtus.tracker.dto;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerModelDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer moduleId;
    private boolean deleted;
    private List<StatusDTO> status;
    private List<AttributeDTO> attributes;
    private List<TransitionDTO> transitions;
    private List<TagDTO> tags;
    private String fileTypesRestrictions;
}
