package br.edu.ufcg.virtus.tracker.dto.simple;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.AttributeDTO;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerModelSimpleDTO extends ModelDTO {

	private Integer id;
	private String name;
	private String fileTypesRestrictions;
	private Integer moduleId;
	private List<AttributeDTO> attributes;
	private List<TransitionDTO> transitions;
}
