package br.edu.ufcg.virtus.core.repository;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.PeopleHistory;

@Repository
public interface PeopleHistoryRepository extends CrudBaseRepository<PeopleHistory, Integer> {

}
