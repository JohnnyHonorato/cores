package br.edu.ufcg.virtus.tracker.dto;

import java.util.Calendar;
import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.simple.TrackerModelSimpleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerDTO extends ModelDTO {

    private Integer id;
    private String title;
    private String description;
    private Calendar dueDate;
    private Boolean delay;
    private StatusDTO status;
    private TransitionDTO transition;
    private TrackerModelSimpleDTO trackerModel;
    private List<AttributeValueDTO> attributesValue;
    private List<TagDTO> tags;
    private List<ChecklistDTO> checklists;
    private List<AssigneeDTO> assignees;
    private List<FilePathDTO> attachments;
    private List<LinkedTrackerDTO> links;

}
