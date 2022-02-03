package br.edu.ufcg.virtus.tracker.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.LinkedTracker;

public interface LinkedTrackerRepository extends CrudBaseRepository<LinkedTracker, Integer>{

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM tracker.tracker_link WHERE second_link = ?1")
	void deleteBySecondLink(Integer trackerId);

}
