package br.edu.ufcg.virtus.business.dto;

import java.util.List;
import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO extends ModelDTO {

	private Integer id;
	private String name;
	private String fantasyName;
	private String description;
	private String governmentCode;
	private FilePathDTO filePath;
	private List<ContactInfoDTO> contacts;
	private Set<PeopleOrganizationDTO> peopleOrganizations;

}