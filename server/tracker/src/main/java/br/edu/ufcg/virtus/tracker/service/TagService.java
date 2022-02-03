package br.edu.ufcg.virtus.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.Tag;
import br.edu.ufcg.virtus.tracker.repository.TagRepository;

@Service
public class TagService extends CrudService<Tag, Integer> {

    @Autowired
    private TagRepository repository;

    @Override
    protected TagRepository getRepository() {
        return this.repository;
    }

    @Override
    protected void preDelete(Integer id) throws BusinessException {
        final Tag tagDB = this.repository.findById(id).get();
        if (tagDB == null) {
            throw new BusinessException("tracker.tag.not.found");
        }
    }

    public List<Tag> findAll(Integer trackerModelId) {
        final List<Tag> tags = this.repository.findByTrackerModelId(trackerModelId);
        return tags;
    }

}
