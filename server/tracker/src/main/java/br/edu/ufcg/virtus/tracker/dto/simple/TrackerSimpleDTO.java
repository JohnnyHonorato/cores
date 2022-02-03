package br.edu.ufcg.virtus.tracker.dto.simple;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackerSimpleDTO extends ModelDTO {

    private Integer id;
    private TransitionDTO transition;
    private TrackerModelSimpleDTO trackerModel;
}
