package br.edu.ufcg.virtus.core.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.PeopleOrganization;

@Repository
public interface PeopleOrganizationRepository extends CrudBaseRepository<PeopleOrganization, Integer> {

    void deleteByPeopleIdAndOrganizationId(Integer peopleId, Integer organizationId);
    
    @Modifying
    @Query(value = "DELETE FROM core.people_organization people_org WHERE people_org.id=?1", nativeQuery = true)    
    void deleteById(Integer id);

    Set<PeopleOrganization> getAllByOrganizationId(Integer organizationId);

}
