package br.edu.ufcg.virtus.tracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;
import br.edu.ufcg.virtus.tracker.repository.TrackerModelRepository;

@Service
public class TrackerModelService extends CrudService<TrackerModel, Integer> {

	@Autowired
	private TrackerModelRepository repository;

	@Autowired
	private StatusService statusService;

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private TransitionService transitionService;

	@Override
	protected TrackerModelRepository getRepository() {
		return this.repository;
	}

	@Override
	public TrackerModel insert(TrackerModel model) throws BusinessException {
		final TrackerModel trackerModel = super.insert(model);
		this.statusService.insertMultipleStatusFromTrackerModel(trackerModel);
		this.attributeService.insertMultipleAttributesFromTrackerModel(trackerModel);
		this.transitionService.insertMultipleTransitionsFromTrackerModel(trackerModel);
		return trackerModel;
	}

	@Override
	public void update(Integer id, TrackerModel trackerModel) throws BusinessException {
		super.update(id, trackerModel);
		this.statusService.insertMultipleStatusFromTrackerModel(trackerModel);
		this.attributeService.insertMultipleAttributesFromTrackerModel(trackerModel);
		this.transitionService.insertMultipleTransitionsFromTrackerModel(trackerModel);
	}

	@Override
	protected TrackerModel preUpdate(TrackerModel model) throws BusinessException {
		model.setStatus(this.statusService.deleteMultipleStatus(model.getStatus()));
		return model;
	}

	@Override
	protected TrackerModel preInsert(TrackerModel model) throws BusinessException {
		model.setStatus(model.getStatus().stream().filter(s -> !s.isDeleted()).collect(Collectors.toList()));
		return super.preInsert(model);
	}

	@Override
	protected void validateInsert(TrackerModel model) throws BusinessException {
		this.validateTrackerModelName(model);
	}

	@Override
	protected void validateUpdate(TrackerModel model) throws BusinessException {
		this.validateTrackerModelName(model);
	}

	private void validateTrackerModelName(TrackerModel model) throws BusinessException {
		if (this.existsWithSameName(model.getName(), model.getId())) {
			throw new BusinessException("tracker-model.name.exists", HttpStatus.BAD_REQUEST);
		}
	}

	private boolean existsWithSameName(String name, Integer id) {
		if (id != null) {
			return this.repository.existsByNameAndIdIsNot(name, id);
		} else {
			return this.repository.existsByName(name);
		}
	}

	public List<TrackerModel> findAll() {
		return (List<TrackerModel>) this.repository.findAll();
	}
	
	public List<TrackerModel> findAllByModule(Integer moduleId) {
		return (List<TrackerModel>) this.repository.findByModuleId(moduleId);
	}

}
