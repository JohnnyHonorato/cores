package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Tracker;

@Repository
public interface TrackerRepository extends CrudBaseRepository<Tracker, Integer> {

	List<Tracker> findByStatusIdOrderByDueDateAsc(Integer statusId);

	List<Tracker> findByStatusIdOrderByDueDateDesc(Integer statusId);

	boolean existsByStatusId(Integer statusId);

	@Query(value = "SELECT * FROM tracker.tracker WHERE tracker_model_id != ?2 and lower(title) LIKE lower(concat('%', ?1,'%'))",
            countQuery = "SELECT * FROM tracker.tracker WHERE tracker_model_id != ?2 and lower(title) LIKE lower(concat('%', ?1,'%'))",
            nativeQuery = true)
    Page<Tracker> searchByTitle(String title, Integer trackerModelId, Pageable pageable);

    default PageListDTO searchByTitle(String title, Integer trackerModelId, SearchFilterDTO filter) {
        final Page<Tracker> result = this.searchByTitle(title, trackerModelId, this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }

    @Query(value = "SELECT * FROM tracker.tracker WHERE tracker_model_id != ?2 and id = ?1",
            countQuery = "SELECT * FROM tracker.tracker WHERE tracker_model_id != ?2 and id = ?1",
            nativeQuery = true)
    Page<Tracker> searchById(Integer id, Integer trackerModelId, Pageable pageable);

    default PageListDTO searchById(Integer id, Integer trackerModelId, SearchFilterDTO filter) {
        final Page<Tracker> result = this.searchById(id, trackerModelId, this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }

}
