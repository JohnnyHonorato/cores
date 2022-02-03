package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.Attribute;

@Repository
public interface AttributeRepository extends CrudBaseRepository<Attribute, Integer> {

    List<Attribute> findAllByTrackerModelIdOrderByPositionAsc(Integer trackerModelId);

    boolean existsByTitleAndIdNotAndTrackerModelIdAndDeletedFalse(String title, Integer id, Integer trackerModelId);

    boolean existsByTitleAndTrackerModelIdAndDeletedFalse(String title, Integer trackerModelId);
    
    Attribute getByTitleAndTrackerModelIdAndDeletedFalse(String title, Integer trackerModelId);
    
    @Query(value = "SELECT att.currency FROM tracker.attribute att WHERE att.tracker_model_id=?1 AND att.title=?2 AND att.deleted=false", nativeQuery = true)
    String getCurrencyByAttributeNameAndTrackerModel(Integer trackerModelId, String attributeName);
}
