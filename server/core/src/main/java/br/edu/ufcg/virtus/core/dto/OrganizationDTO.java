package br.edu.ufcg.virtus.core.dto;

import java.util.List;
import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.model.ContactInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO extends ModelDTO {

    private Integer id;
    private String name;
    private String fantasyName;
    private String governmentCode;
    private String description;
    private FilePathDTO filePath;
    private List<ContactInfo> contacts;
    private Set<PeopleOrganizationDTO> peopleOrganizations;

}
