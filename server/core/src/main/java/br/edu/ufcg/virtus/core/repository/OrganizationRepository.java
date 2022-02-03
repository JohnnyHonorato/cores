package br.edu.ufcg.virtus.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.Organization;

@Repository
public interface OrganizationRepository extends CrudBaseRepository<Organization, Integer> {

    @Query(value = "SELECT * FROM organization WHERE government_code = ?1 AND deleted = false", nativeQuery = true)
    Organization findOneByCnpj(String cnpj);

    boolean existsByGovernmentCodeAndDeletedIsFalseAndIdIsNot(String string, Integer integer);

    boolean existsByGovernmentCodeAndDeletedIsFalse(String string);

}
