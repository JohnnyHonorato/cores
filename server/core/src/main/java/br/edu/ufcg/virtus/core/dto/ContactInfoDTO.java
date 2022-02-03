package br.edu.ufcg.virtus.core.dto;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.core.domain.ContactDomain;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.domain.ContactInfoType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfoDTO extends ModelDTO {

    private Integer id;

    private ContactInfoType contactInfoType;

    private String email;

    private String emailTag;

    private String phone;

    private String phoneTag;
    
    private String phoneCountryCode;

    private ContactInfoOwnerType ownerType;

    private Integer ownerId;

    private String addressStreet;

    private String addressComplement;

    private String addressNeighborhood;

    private ContactDomain contactDomain;

    private String addressZipCode;

    private String addressCity;

    private String addressNumber;

    private String addressState;

    private String addressCountry;

}
