package br.edu.ufcg.virtus.tracker.repository;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FilterRepository extends CrudBaseRepository<Filter, Integer> {

	boolean existsByCreatedByAndTrackerModelIdAndName(Integer userId, Integer trackerModelId, String name);

	boolean existsByCreatedByAndTrackerModelIdAndIdNotAndName(Integer userId, Integer trackerModelId, Integer id,
			String name);

	@Query(value = "SELECT f.* FROM tracker.filter f WHERE f.tracker_model_id = ?1 AND f.created_by = ?2 AND f.deleted = false ORDER BY f.favourite DESC, f.created_on DESC",
			countQuery = "SELECT COUNT(f.*) FROM tracker.filter f WHERE f.tracker_model_id = ?1 AND f.created_by = ?2 AND f.deleted = false", nativeQuery = true)
	Page<Filter> search(Integer trackerModelId, Integer loggedUserId, Pageable page);

	default PageListDTO search(SearchFilterDTO filter, Integer trackerModelId, Integer loggedUserId) {
		final Page<Filter> result = this.search(trackerModelId, loggedUserId, this.page(filter));
		return new PageListDTO(result.getTotalPages(), result.getContent());
	}

	@Query(value = "SELECT COUNT(f.*) FROM tracker.filter f WHERE f.tracker_model_id = ?1 AND f.created_by = ?2 AND f.deleted = false", nativeQuery = true)
	Integer findNumberFiltersByTrackerModelId(Integer trackerModelId, Integer loggedUserId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE tracker.filter f SET favourite=?1 WHERE f.id=?2 AND f.tracker_model_id=?3 AND f.deleted = false", nativeQuery = true)
	void updatePartialIsFavoriteAttribute(Boolean value, Integer id, Integer trackerModelId);
	
	@Query(value = "SELECT COUNT(f.*) FROM tracker.filter f WHERE f.tracker_model_id = ?1 AND f.favourite = true AND f.deleted = false", nativeQuery = true)
	Integer findTheNumberOfFavoritesByTrackerModelId(Integer trackerModelId);
}
