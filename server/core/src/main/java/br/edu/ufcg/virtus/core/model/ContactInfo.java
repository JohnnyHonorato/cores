package br.edu.ufcg.virtus.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.core.domain.ContactDomain;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.domain.ContactInfoType;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "contact_info")
@SQLDelete(sql = "UPDATE contact_info SET deleted = true WHERE id = ?")
@Where(clause = Model.WHERE_DELETED_CLAUSE)
@Getter
@Setter
public class ContactInfo extends Model<Integer> {

    private static final String PHONE_COUNTRY_CODE_PATTERN = "^[+]\\d{1,3}$";
    private static final String PHONE_NUMBER_PATTERN = "^[0-9- ]*$";

    private static final String ADDRESS_ZIP_CODE_PATTERN = "^\\d{8}$";

    private static final String SIMPLE_EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contact_info_type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{contact-info.type.required}")
    private ContactInfoType contactInfoType;

    @Column(name = "phone")
    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = "{contact-info.phone.country-code.pattern}")
    @Size(max = 20, message = "{contact-info.phone.number.length}")
    private String phone;

    @Column(name = "phone_tag")
    private String phoneTag;

    @Column(name = "phone_country_code")
    @Pattern(regexp = PHONE_COUNTRY_CODE_PATTERN, message = "{contact-info.phone.country-code.pattern}")
    private String phoneCountryCode;

    @Column(name = "deleted")
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type")
    @NotNull(message = "{contact-info.owner-type.required}")
    private ContactInfoOwnerType ownerType;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "address_street")
    @Size(max = 255, message = "{contact-info.address.street.length}")
    private String addressStreet;

    @Column(name = "address_complement")
    @Size(max = 100, message = "{contact-info.address.complement.length}")
    private String addressComplement;

    @Column(name = "address_neighborhood")
    @Size(max = 100, message = "{contact-info.address.neighborhood.length}")
    private String addressNeighborhood;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_domain")
    private ContactDomain contactDomain;

    @Column(name = "address_zip_code")
    @Pattern(regexp = ADDRESS_ZIP_CODE_PATTERN, message = "{contact-info.address.zip-code.pattern}")
    private String addressZipCode;

    @Column(name = "address_city")
    @Size(max = 100, message = "{contact-info.address.city.length}")
    private String addressCity;

    @Column(name = "address_number")
    @Size(max = 255, message = "{contact-info.address.number.length}")
    private String addressNumber;

    @Column(name = "address_state")
    @Size(max = 255, message = "{contact-info.address.state.length}")
    private String addressState;

    @Column(name = "address_country")
    @Size(max = 255, message = "{contact-info.address.country.length}")
    private String addressCountry;

    @Column(name = "email")
    @Email(message = "{contact-info.email.address.pattern}")
    @Pattern(regexp = SIMPLE_EMAIL_PATTERN, message = "{contact-info.email.address.pattern}")
    private String email;

    @Column(name = "email_tag")
    private String emailTag;

}
