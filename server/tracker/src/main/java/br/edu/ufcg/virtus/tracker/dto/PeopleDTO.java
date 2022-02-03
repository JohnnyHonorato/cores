package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleDTO extends ModelDTO {

    private Integer id;

    private String name;

    private String governmentCode;

    private String nickname;

    private boolean deleted;

}
