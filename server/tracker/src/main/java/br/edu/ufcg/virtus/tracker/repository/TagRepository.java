package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Tag;

@Repository
public interface TagRepository extends CrudBaseRepository<Tag, Integer> {
    List<Tag> findByTrackerModelId(Integer trackerModelId);

    void deleteByIdAndTrackerModelId(Integer id, Integer trackerModelId);

    @Query(value = "SELECT t.* FROM tracker.tag t "
            + "INNER JOIN tracker.tracker_tag tt ON tt.tag_id = t.id "
            + "INNER JOIN tracker.tracker tr ON tt.tracker_id = tr.id "
            + "WHERE t.id=?1", nativeQuery = true)
    List<Tag> findTrackerTagById(Integer tagId);
}
