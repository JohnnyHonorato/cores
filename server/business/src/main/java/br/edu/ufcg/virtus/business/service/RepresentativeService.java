package br.edu.ufcg.virtus.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.business.model.Representative;
import br.edu.ufcg.virtus.business.repository.RepresentativeRepository;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;

@Service
public class RepresentativeService extends CrudService<Representative, Integer> {

	@Autowired
	private RepresentativeRepository repository;

	@Override
	protected RepresentativeRepository getRepository() {
		return repository;
	}

	@Override
	public Representative insert(Representative model) throws BusinessException {

		return super.insert(model);
	}

	public List<Representative> getRepresentativesByBusinessOpportunity(Integer id) {
		return this.repository.getRepresentativesByBusinessOpportunity(id);
	}

	public void removeAllRepresentativeFromBusinessOpportunity(Integer id) {
		this.repository.removeAllRepresentativeFromBusinessOpportunity(id);
	}
}
