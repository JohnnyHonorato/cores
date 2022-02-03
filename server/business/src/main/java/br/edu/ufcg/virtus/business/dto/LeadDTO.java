package br.edu.ufcg.virtus.business.dto;

import java.util.List;
import java.util.Set;

import br.edu.ufcg.virtus.business.domain.PeopleType;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadDTO extends ModelDTO {

    private Integer id;

    private String name;

    private String nickname;

    private PeopleType peopleType;

    private String governmentCode;

    private List<ContactInfoDTO> contacts;

    private Set<LeadOrganizationDTO> peopleOrganizations;

    private UserDTO user;
}
