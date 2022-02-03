package br.edu.ufcg.virtus.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.business.model.Representative;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;

@Repository
public interface RepresentativeRepository extends CrudBaseRepository<Representative, Integer>{

	@Query(value = "SELECT * FROM business.representative rep "
            + "WHERE rep.business_opportunity_id=?1 "
			+ "AND rep.deleted=false", nativeQuery = true)
	List<Representative> getRepresentativesByBusinessOpportunity(Integer id);
	
	@Modifying
	@Query(value = "UPDATE business.representative "
            + "SET deleted=true "
			+ "WHERE business_opportunity_id=?1", nativeQuery = true)
	void removeAllRepresentativeFromBusinessOpportunity(Integer id);

}