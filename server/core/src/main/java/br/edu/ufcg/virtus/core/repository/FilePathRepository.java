package br.edu.ufcg.virtus.core.repository;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.FilePath;

@Repository
public interface FilePathRepository extends CrudBaseRepository<FilePath, Integer> {

}
