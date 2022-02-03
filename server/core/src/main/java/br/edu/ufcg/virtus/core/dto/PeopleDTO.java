package br.edu.ufcg.virtus.core.dto;

import java.util.List;
import java.util.Set;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.domain.PeopleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleDTO extends ModelDTO {

    private Integer id;

    private String name;

    private String governmentCode;

    private PeopleType peopleType;

    private String nickname;

    private boolean deleted;

    private Set<PeopleOrganizationDTO> peopleOrganizations;

    private UserDTO user;

    private List<ContactInfoDTO> contacts;

}
