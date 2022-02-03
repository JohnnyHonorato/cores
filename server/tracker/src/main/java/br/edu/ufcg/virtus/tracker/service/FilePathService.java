package br.edu.ufcg.virtus.tracker.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.FilePath;
import br.edu.ufcg.virtus.tracker.repository.FilePathRepository;

@Service
public class FilePathService extends CrudService<FilePath, Integer> {

    @Autowired
    private FilePathRepository repository;

	@Override
	protected CrudBaseRepository<FilePath, Integer> getRepository() {
		return this.repository;
	}

	public List<FilePath> getFilePathByTrackerId(Integer trackerId) {
		List<FilePath> files = this.repository.findAllByTrackerId(trackerId);
		files.sort(Comparator.comparing(FilePath::getCreatedOn).reversed());
		return files;
	}

}
