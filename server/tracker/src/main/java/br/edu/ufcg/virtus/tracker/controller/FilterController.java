package br.edu.ufcg.virtus.tracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import br.edu.ufcg.virtus.tracker.dto.FilterDTO;
import br.edu.ufcg.virtus.tracker.model.Filter;
import br.edu.ufcg.virtus.tracker.service.FilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("tracker-models/{trackerModelId}/filters")
@Authorization("filter")
@Api(value = "filter", tags = "filter-controller")
public class FilterController extends CrudBaseController<Filter, Integer, FilterDTO> {

	@Autowired
	private FilterService service;

	@Override
	protected FilterService getService() {
		return this.service;
	}

	@Override
	protected PageListDTO searchInService(HttpServletRequest request, SearchFilterDTO filter) {
		final Integer trackerModelId = Integer.valueOf(this.getPathVariable(request, "trackerModelId"));
		return this.service.search(filter, trackerModelId);
	}

	@GetMapping("/count")
	@ApiOperation(value = "Count all filters")
	@ReadPermission
	public Integer getNumberFiltersByTrackerModelId(HttpServletRequest request) {
		final Integer trackerModelId = Integer.valueOf(this.getPathVariable(request, "trackerModelId"));
		return this.service.findNumberFiltersByTrackerModelId(trackerModelId);
	}

	@ApiOperation(value = "Update attribute favourite")
	@PatchMapping("/{id}/favourite/{value}")
	@UpdatePermission
	public ResponseEntity<?> patchIsFavoriteAttribute(HttpServletRequest request) throws BusinessException {
		final Integer trackerModelId = Integer.valueOf(this.getPathVariable(request, "trackerModelId"));
		final Integer id = Integer.valueOf(this.getPathVariable(request, "id"));
		final Boolean value = Boolean.valueOf(this.getPathVariable(request, "value"));
		this.service.updatePartialIsFavoriteAttribute(value, id, trackerModelId);
		return this.success();
	}

}
