package br.edu.ufcg.virtus.tracker.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.ChecklistItem;

public interface ChecklistItemRepository  extends CrudBaseRepository<ChecklistItem, Integer> {
	
	@Query(value = "UPDATE tracker.checklist_item SET done = ?2 WHERE id = ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void updateChecklistItemValue(Integer id, Boolean done);

}
