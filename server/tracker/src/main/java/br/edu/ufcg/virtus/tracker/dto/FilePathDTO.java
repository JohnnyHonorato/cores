package br.edu.ufcg.virtus.tracker.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePathDTO extends ModelDTO {

    private Integer id;

    private String name;
    
    private String originalName;

    private Integer fileSize;

    private String directory;

    private String contentType;

    public FilePathDTO() {
    }

    public FilePathDTO(String name, String directory) {
        this.name = name;
        this.directory = directory;
    }
}
