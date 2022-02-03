package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.FilePath;

public interface FilePathRepository extends CrudBaseRepository<FilePath, Integer>{
    FilePath findById(Integer id);

    List<FilePath> findByIdIn(List<Integer> ids);

    @Query( value = "SELECT tracker.filepath.*" +
            "FROM tracker.filepath join tracker.tracker on tracker.tracker.id = tracker.filepath.tracker_id where tracker.tracker.id = ?1" ,nativeQuery = true)
    List<FilePath> findAllByTrackerId(Integer trackerId);

}
