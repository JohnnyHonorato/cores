package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Assignee;

public interface AssigneeRepository extends CrudBaseRepository<Assignee, Integer> {

    @Query(value = "SELECT a.* FROM tracker.assignee a JOIN tracker.tracker_assignee ta ON ta.assignee_id = a.id WHERE ta.tracker_id = ?1", nativeQuery = true)
    List<Assignee> findByTrackerId(Integer id);
    
    @Modifying
	@Query(value = "DELETE FROM tracker.tracker_assignee WHERE tracker_id = ?1 and assignee_id = ?2", nativeQuery = true)
    void removeTrackerAssigneeRelationship(Integer trackerId, Integer assigneeId);
    
}
