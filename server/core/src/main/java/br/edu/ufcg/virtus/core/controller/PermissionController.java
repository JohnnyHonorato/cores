package br.edu.ufcg.virtus.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.core.dto.PermissionDTO;
import br.edu.ufcg.virtus.core.model.Permission;
import br.edu.ufcg.virtus.core.service.PermissionService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("permissions")
@Authorization("permission")
@Api(value = "permission", tags = "permission-controller")
public class PermissionController extends CrudBaseController<Permission, Integer, PermissionDTO> {

	@Autowired
	private PermissionService service;

	@Override
	protected PermissionService getService() {
		return this.service;
	}
}
