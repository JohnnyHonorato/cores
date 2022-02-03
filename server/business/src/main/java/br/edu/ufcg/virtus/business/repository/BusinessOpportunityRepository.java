package br.edu.ufcg.virtus.business.repository;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.business.model.BusinessOpportunity;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;

@Repository
public interface BusinessOpportunityRepository extends CrudBaseRepository<BusinessOpportunity, Integer>{
    
    boolean existsByTitleAndIdIsNot(String string, Integer integer);
    
    boolean existsByTitle(String string);
}