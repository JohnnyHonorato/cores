package br.edu.ufcg.virtus.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.GenericEntity;

@Repository
public interface BusinessRepository extends CrudBaseRepository<GenericEntity, Integer> {

	@Query(value = "SELECT CASE WHEN EXISTS  " + "(SELECT * FROM business.business_opportunity bo "
			+ "WHERE bo.organization_id=?1 AND bo.deleted=false) " + "THEN CAST(1 AS BIT) "
			+ "ELSE CAST(0 AS BIT) END", nativeQuery = true)
	Boolean existsOpportunityForOrganization(Integer organizationId);
}
