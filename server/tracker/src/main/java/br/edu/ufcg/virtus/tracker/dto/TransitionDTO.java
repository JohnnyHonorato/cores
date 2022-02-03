package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransitionDTO extends ModelDTO {

	private Integer id;
	private StatusDTO source;
	private StatusDTO target;
	private String attributes;
	private boolean deleted;
}
