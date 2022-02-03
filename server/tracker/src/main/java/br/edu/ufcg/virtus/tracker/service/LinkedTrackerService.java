package br.edu.ufcg.virtus.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.model.LinkedTracker;
import br.edu.ufcg.virtus.tracker.repository.LinkedTrackerRepository;

@Service
public class LinkedTrackerService extends CrudService<LinkedTracker, Integer> {
	
	@Autowired
	private LinkedTrackerRepository repository;

	@Override
	protected CrudBaseRepository<LinkedTracker, Integer> getRepository() {
		return this.repository;
	}
	
	void deleteTrackerLinkBySecondLink(Integer trackerId) {
		this.repository.deleteBySecondLink(trackerId);
	}

}
