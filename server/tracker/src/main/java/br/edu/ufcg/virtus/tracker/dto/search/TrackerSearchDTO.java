package br.edu.ufcg.virtus.tracker.dto.search;

import java.util.Calendar;
import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSearchDTO extends ModelDTO {

	private Integer id;
	private String title;
	private String description;
	private TransitionDTO transition;
	private List<AttributeValueDTO> attributesValue;
	private List<TagDTO> tags;
	private List<AssigneeDTO> assignees;
	private Integer numberComments;
	private StatusDTO status;
	private Boolean delay;
	private Calendar dueDate;
	private List<FilePathDTO> attachments;
	private TrackerModelDTO trackerModel;
	private List<LinkedTrackerDTO> links;
}
