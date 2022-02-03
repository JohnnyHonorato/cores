package br.edu.ufcg.virtus.tracker.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.Status;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;
import br.edu.ufcg.virtus.tracker.repository.StatusRepository;
import br.edu.ufcg.virtus.tracker.repository.TrackerRepository;

@Service
public class StatusService extends CrudService<Status, Integer> {

	private final Integer STATUS_MIN_PERMITTED_QTD = 2;

	@Autowired
	private StatusRepository repository;

	@Autowired
	private TrackerRepository trackerRepository;

	@Override
	protected StatusRepository getRepository() {
		return this.repository;
	}

	public void insertMultipleStatusFromTrackerModel(TrackerModel model) throws BusinessException {
		this.validateStatusList(model.getStatus());
		for (final Status status : model.getStatus()) {
			status.setTrackerModel(model);
			this.insert(status);
		}
	}

	public List<Status> deleteMultipleStatus(List<Status> lista) throws BusinessException {
        List<Status> statusList = new ArrayList<Status>();
        for (Status status : lista) {
            if (!status.isDeleted()) {
                statusList.add(status);
            }
        }
        
        this.validateStatusList(statusList);
        
        for (Status status : lista) {
            if (status.isDeleted()) {
                this.delete(status.getId());
            }
        }
        
        return statusList;
    }

	@Override
	public void delete(Integer id) throws BusinessException {
		if (this.trackerRepository.existsByStatusId(id)) {
			throw new BusinessException("tracker-model.status.associated", HttpStatus.BAD_REQUEST);
		} else {
			this.repository.deleteById(id);
		}
	}

	public Status getStatusByIdTrackerModelAndName(Integer trackerModelId, String name) {
		return this.repository.findStatusByIdTrackerModelAndName(trackerModelId, name);
	}

	private void validateStatusList(List<Status> status) throws BusinessException {
		if (status == null || status.isEmpty()) {
			throw new BusinessException("tracker-model.status-list.empty", HttpStatus.BAD_REQUEST);
		}

		if (status.size() < this.STATUS_MIN_PERMITTED_QTD) {
			throw new BusinessException("tracker-model.status.less-then-min", HttpStatus.BAD_REQUEST);
		}

		final List<String> names = status.stream().map(Status::getName).collect(Collectors.toList());
		for (final String name : names) {
			if (Collections.frequency(names, name) > 1) {
				throw new BusinessException("tracker-model.status.repeated.name", HttpStatus.BAD_REQUEST);
			}
		}
	}

}
