package br.edu.ufcg.virtus.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.model.LoggedUser;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.common.util.MapperUtil;
import br.edu.ufcg.virtus.common.util.SecurityContextUtil;
import br.edu.ufcg.virtus.tracker.dto.FilterDTO;
import br.edu.ufcg.virtus.tracker.model.Filter;
import br.edu.ufcg.virtus.tracker.repository.CoreRepository;
import br.edu.ufcg.virtus.tracker.repository.FilterRepository;

@Service
public class FilterService extends CrudService<Filter, Integer> {

	@Autowired
	private FilterRepository repository;

	@Autowired
	private CoreRepository coreRepository;

	@Override
	protected FilterRepository getRepository() {
		return this.repository;
	}

	@Override
	public void validateInsert(Filter model) throws BusinessException {
		LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
		if (this.repository.existsByCreatedByAndTrackerModelIdAndName(loggedUser.getId(),
				model.getTrackerModel().getId(), model.getName())) {
			throw new BusinessException("filter.name.exists", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void validateUpdate(Filter model) throws BusinessException {
		if (this.repository.existsByCreatedByAndTrackerModelIdAndIdNotAndName(model.getCreatedBy(),
				model.getTrackerModel().getId(), model.getId(), model.getName())) {
			throw new BusinessException("filter.name.exists", HttpStatus.BAD_REQUEST);
		}
	}

	public PageListDTO search(SearchFilterDTO filter, Integer trackerModelId) {
		LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
		PageListDTO dto = this.getRepository().search(filter, trackerModelId, loggedUser.getId());
		List<FilterDTO> list = MapperUtil.toList(dto.getItems(), FilterDTO.class);
		list.forEach(item -> {
			item.getAssignees().forEach(assignee -> { // TODO Refatorar usando requisição para o core.
				String name = this.coreRepository.getPersonNameById(assignee.getPeopleId());
				assignee.setName(name);
			});
		});
		dto.setItems(list);
		return dto;
	}

	public Integer findNumberFiltersByTrackerModelId(Integer trackerModelId) {
		LoggedUser loggedUser = SecurityContextUtil.getLoggedUser();
		return this.repository.findNumberFiltersByTrackerModelId(trackerModelId,  loggedUser.getId());
	}

	public void updatePartialIsFavoriteAttribute(Boolean value, Integer id, Integer trackerModelId)
			throws BusinessException {
		Integer numberOfFavorites = this.repository.findTheNumberOfFavoritesByTrackerModelId(trackerModelId);
		if (value.equals(true) && numberOfFavorites >= 5) {
			throw new BusinessException("filter.extrapolated.quantity", HttpStatus.BAD_REQUEST);
		}
		this.repository.updatePartialIsFavoriteAttribute(value, id, trackerModelId);
	}
}
