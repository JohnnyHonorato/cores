package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Status;

@Repository
public interface StatusRepository extends CrudBaseRepository<Status, Integer> {

	@Query(value = "SELECT * FROM tracker.status WHERE tracker_model_id = ?1", nativeQuery = true)
	List<Status> findStatusByIdTrackerModel(Integer trackerModelId);
	
	@Query(value = "SELECT * FROM tracker.status WHERE tracker_model_id = ?1 and name = ?2", nativeQuery = true)
	Status findStatusByIdTrackerModelAndName(Integer trackerModelId, String name);
}
