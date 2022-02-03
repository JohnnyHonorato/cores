package br.edu.ufcg.virtus.tracker.dto.search;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchDTO extends ModelDTO {

	private Integer id;
	private String text;
}
