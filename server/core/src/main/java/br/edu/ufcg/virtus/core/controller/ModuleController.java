package br.edu.ufcg.virtus.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.core.dto.ModuleDTO;
import br.edu.ufcg.virtus.core.dto.search.ModuleSearchDTO;
import br.edu.ufcg.virtus.core.model.Module;
import br.edu.ufcg.virtus.core.service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("modules")
@Authorization("module")
@Api(value = "module", tags = "module-controller")
public class ModuleController extends CrudBaseController<Module, Integer, ModuleDTO> {

	@Autowired
	private ModuleService service;

	@Override
	protected ModuleService getService() {
		return this.service;
	}

	@GetMapping("/all")
	@ApiOperation(value = "Find all active modules")
	public ResponseEntity<List<ModuleDTO>> findAll() {
		final List<Module> modules = this.service.findAll();
		return new ResponseEntity<>(this.toList(modules, ModuleDTO.class), HttpStatus.OK);
	}

	@Override
	protected Class<ModuleSearchDTO> getSearchDTOClass() {
		return ModuleSearchDTO.class;
	}
}
