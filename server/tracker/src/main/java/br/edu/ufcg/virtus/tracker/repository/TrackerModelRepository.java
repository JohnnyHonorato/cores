package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;

@Repository
public interface TrackerModelRepository extends CrudBaseRepository<TrackerModel, Integer> {

    boolean existsByNameAndIdIsNot(String string, Integer integer);

    boolean existsByName(String string);
    
    List<TrackerModel> findByModuleId(Integer moduleId);
}
