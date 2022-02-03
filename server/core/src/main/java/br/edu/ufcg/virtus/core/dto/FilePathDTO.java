package br.edu.ufcg.virtus.core.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePathDTO extends ModelDTO {

    private Integer id;

    private String name;

    public String originalName;

	private Integer fileSize;

	private String directory;

    private String contentType;

    public FilePathDTO() {
    }

    public FilePathDTO(String name, String directory, String originalName, Integer fileSize, String contentType) {
        this.name = name;
        this.directory = directory;
        this.originalName = originalName;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }
}
