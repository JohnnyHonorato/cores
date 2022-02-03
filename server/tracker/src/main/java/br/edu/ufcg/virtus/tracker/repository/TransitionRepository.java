package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Transition;

@Repository
public interface TransitionRepository extends CrudBaseRepository<Transition, Integer> {

	@Query(value = "SELECT t.* FROM tracker.transition t WHERE t.source_id=:sourceId AND t.target_id=:targetId AND t.deleted=false", nativeQuery = true)
    Transition findBySourceIdAndTargetId(Integer sourceId, Integer targetId);

    List<Transition> findByTrackerModelId(Integer trackerModelId);

    @Query(value = "DELETE FROM tracker.transition WHERE (source_id=:sourceId or target_id=:targetId) and tracker_model_id=:trackerModelId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteTransitions(@Param("sourceId") Integer statusId, @Param("targetId") Integer targetId,
            @Param("trackerModelId") Integer trackerModelId);

}
