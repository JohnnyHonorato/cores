package br.edu.ufcg.virtus.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileWrapperDTO {

    private String fileName;

    private String mimeType;

    private long fileLength;

    private byte[] fileBytes;

}
