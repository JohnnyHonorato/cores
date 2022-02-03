package br.edu.ufcg.virtus.tracker.dto;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDTO extends ModelDTO{
	
	private Integer id;
	private String name;
	private Boolean isFavourite;
	private Integer numberOfAttributes;
	private TrackerModelDTO trackerModel;
	private Integer createdBy;
	private List<TagDTO> tags;
	private List<AssigneeDTO> assignees;
	private Boolean deleted;
}
