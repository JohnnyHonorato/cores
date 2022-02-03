package br.edu.ufcg.virtus.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.virtus.business.model.BusinessOpportunity;
import br.edu.ufcg.virtus.business.model.Representative;
import br.edu.ufcg.virtus.business.repository.BusinessOpportunityRepository;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;

@Service
public class BusinessOpportunityService extends CrudService<BusinessOpportunity, Integer> {

	@Autowired
	private BusinessOpportunityRepository repository;

	@Autowired
	private RepresentativeService representativeServie;

	@Override
	public BusinessOpportunityRepository getRepository() {
		return repository;
	}

	@Transactional(rollbackFor = { RuntimeException.class, BusinessException.class })
	public BusinessOpportunity insertWithRepresentativeList(BusinessOpportunity businessOpportunity,
			List<Representative> representatives) throws BusinessException {
		final BusinessOpportunity businessCreated = super.insert(businessOpportunity);
		saveRepresentatives(businessCreated, representatives);
		return businessCreated;
	}

	@Transactional(rollbackFor = { RuntimeException.class, BusinessException.class })
	public void updateWithRepresentativeList(Integer id, BusinessOpportunity businessOpportunity,
			List<Representative> representatives) throws BusinessException {
		super.update(id, businessOpportunity);
		this.representativeServie.removeAllRepresentativeFromBusinessOpportunity(id);
		saveRepresentatives(businessOpportunity, representatives);
	}

	private void saveRepresentatives(BusinessOpportunity businessOpportunity, List<Representative> representatives)
			throws BusinessException {
		for (Representative representative : representatives) {			
			representative.setBusinessOpportunity(businessOpportunity);
			this.representativeServie.insert(representative);
		}
	}
	
    @Override
    protected void validateInsert(BusinessOpportunity model) throws BusinessException {
        this.validateBusinessOpportunity(model);
    }

    @Override
    protected void validateUpdate(BusinessOpportunity model) throws BusinessException {
        this.validateBusinessOpportunity(model);
    }

    private void validateBusinessOpportunity(BusinessOpportunity businessOpportunity) throws BusinessException {
        if (this.existsWithSameTitle(businessOpportunity.getTitle(), businessOpportunity.getId())) {
            throw new BusinessException("business-opportunity.title.exists", HttpStatus.BAD_REQUEST);
        }
        if (businessOpportunity.getTitle().isEmpty() || businessOpportunity.getDescription().isEmpty() || businessOpportunity.getOrganizationId()==null) {
            throw new BusinessException("business-opportunity.required", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean existsWithSameTitle(String name, Integer id) {
        if (id != null) {
            return this.repository.existsByTitleAndIdIsNot(name, id);
        } else {
            return this.repository.existsByTitle(name);
        }
    }
}
