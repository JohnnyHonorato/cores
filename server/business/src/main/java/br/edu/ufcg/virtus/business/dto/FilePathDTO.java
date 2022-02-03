package br.edu.ufcg.virtus.business.dto;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePathDTO extends ModelDTO {
	
	private Integer id;

	private String name;

	private String directory;
	
	private boolean deleted;
}
