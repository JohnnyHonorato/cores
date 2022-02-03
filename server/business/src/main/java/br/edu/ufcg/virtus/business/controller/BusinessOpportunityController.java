package br.edu.ufcg.virtus.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.business.dto.BusinessOpportunityDTO;
import br.edu.ufcg.virtus.business.dto.RepresentativeDTO;
import br.edu.ufcg.virtus.business.model.BusinessOpportunity;
import br.edu.ufcg.virtus.business.model.Representative;
import br.edu.ufcg.virtus.business.service.BusinessOpportunityService;
import br.edu.ufcg.virtus.business.service.RepresentativeService;
import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.InsertPermission;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("business-opportunity")
@Authorization("business-opportunity")
@Api(value = "business-opportunity", tags = "business-opportunity-controller")
public class BusinessOpportunityController
		extends CrudBaseController<BusinessOpportunity, Integer, BusinessOpportunityDTO> {

	@Autowired
	private BusinessOpportunityService businessOportunityService;

	@Autowired
	private RepresentativeService representativeService;

	@Override
	protected BusinessOpportunityService getService() {
		return this.businessOportunityService;
	}

	@Override
	@ApiOperation(value = "Add an item")
	@PostMapping
	@InsertPermission
	public ResponseEntity<BusinessOpportunityDTO> insert(HttpServletRequest request,
			@Valid @RequestBody BusinessOpportunityDTO modelDTO) throws BusinessException {
		final BusinessOpportunity modelCreated = this.getService().insertWithRepresentativeList(this.toModel(modelDTO),
				this.toList(modelDTO.getRepresentatives(), Representative.class));
		BusinessOpportunityDTO responseDTO = this.toDTO(modelCreated);
		responseDTO.setRepresentatives(modelDTO.getRepresentatives());
		return this.created(responseDTO);
	}

	@Override
	@ApiOperation(value = "Get a item with an ID")
	@GetMapping("/{id}")
	@ReadPermission
	public BusinessOpportunityDTO getOne(HttpServletRequest request, @PathVariable Integer id)
			throws BusinessException {
		BusinessOpportunityDTO businessOpportunityDTO = this.toDTO(this.getOneModel(id));
		List<RepresentativeDTO> representativeDTOs = this.toList(
				this.representativeService.getRepresentativesByBusinessOpportunity(id), RepresentativeDTO.class);
		businessOpportunityDTO.setRepresentatives(representativeDTOs);
		return businessOpportunityDTO;
	}

	@Override
	@ApiOperation(value = "Update an item")
	@PutMapping("/{id}")
	@UpdatePermission
	public ResponseEntity<BusinessOpportunityDTO> update(HttpServletRequest request, @Valid @PathVariable Integer id,
			@RequestBody BusinessOpportunityDTO modelDTO) throws BusinessException {
		this.getService().updateWithRepresentativeList(id, this.toModel(modelDTO),
				this.toList(modelDTO.getRepresentatives(), Representative.class));		
		List<RepresentativeDTO> representativeDTOs = this.toList(
				this.representativeService.getRepresentativesByBusinessOpportunity(modelDTO.getId()),
				RepresentativeDTO.class);
		modelDTO.setRepresentatives(representativeDTOs);
		return this.ok(modelDTO);
	}

}