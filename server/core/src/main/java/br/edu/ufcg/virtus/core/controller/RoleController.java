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
import br.edu.ufcg.virtus.core.dto.RoleDTO;
import br.edu.ufcg.virtus.core.dto.search.RoleSearchDTO;
import br.edu.ufcg.virtus.core.model.Role;
import br.edu.ufcg.virtus.core.service.RoleService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("roles")
@Authorization("role")
@Api(value = "role", tags = "role-controller")
public class RoleController extends CrudBaseController<Role, Integer, RoleDTO> {

    @Autowired
    private RoleService service;

    @Override
    protected RoleService getService() {
        return this.service;
    }

    @Override
    protected Class<RoleSearchDTO> getSearchDTOClass() {
        return RoleSearchDTO.class;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> findAll() {
        final List<Role> roles = this.service.findAll();
        return new ResponseEntity<>(this.toList(roles, RoleDTO.class), HttpStatus.OK);
    }

}
