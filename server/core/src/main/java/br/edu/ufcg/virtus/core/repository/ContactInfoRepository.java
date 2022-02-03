package br.edu.ufcg.virtus.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.model.ContactInfo;

@Repository
public interface ContactInfoRepository extends CrudBaseRepository<ContactInfo, Integer>{

    @Query(nativeQuery = true ,value = "SELECT * FROM core.contact_info ci WHERE ci.owner_id = ?1 AND ci.owner_type = ?2 AND ci.deleted = false;")
    List<ContactInfo> searchByOwnerIdAndOwnerType(Integer id, String organization);

    default List<ContactInfo> getAllByOwnerIdAndOwnerType(Integer id, ContactInfoOwnerType organizationType) {
        return this.searchByOwnerIdAndOwnerType(id, organizationType.name());
    }

    @Query(nativeQuery = true, value = "SELECT * FROM core.contact_info ci WHERE ci.email = ?1 AND ci.email_tag = 'MAIN' AND owner_type = 'MEMBER' AND ci.deleted = false")
    ContactInfo findByInstitutionalEmailAndMemberOwnerTypeAndNotDeleted(String institutionalEmail);

    default ContactInfo getOneByInstitutionalEmailAndMemberOwnerTypeAndNotDeleted(String institutionalEmail) {
        return this.findByInstitutionalEmailAndMemberOwnerTypeAndNotDeleted(institutionalEmail);
    }
    
    @Query(nativeQuery = true, value = "SELECT count(*) FROM core.contact_info ci WHERE ci.owner_id = ?1 AND contact_info_type = 'EMAIL' AND ci.email_tag = 'MAIN' AND ci.deleted = false")
    Integer countMainEmailByOwnerId(Integer ownerId);

}
