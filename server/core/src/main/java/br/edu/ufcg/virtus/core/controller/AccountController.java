package br.edu.ufcg.virtus.core.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ufcg.virtus.common.controller.BaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.dto.ModuleDTO;
import br.edu.ufcg.virtus.core.dto.PermissionDTO;
import br.edu.ufcg.virtus.core.model.Module;
import br.edu.ufcg.virtus.core.model.Permission;
import br.edu.ufcg.virtus.core.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("accounts")
@Api(value = "account", tags = "account-controller")
public class AccountController extends BaseController {

    @Autowired
    private AccountService service;

    @GetMapping("/modules")
    @ApiOperation(value = "Get all modules of the logged user")
    public ResponseEntity<List<ModuleDTO>> findAllPermitedModules(HttpServletRequest request) throws BusinessException {
        final List<Module> modules = this.service.findAllModules(request);
        return new ResponseEntity<>(this.toList(modules, ModuleDTO.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves all user permissions")
    @GetMapping("/permissions")
    public ResponseEntity<Set<PermissionDTO>> getPermissions(HttpServletRequest request) throws BusinessException {
        final Set<Permission> permissions = this.service.getPermissions(request);
        return new ResponseEntity<>(this.toSet(permissions, PermissionDTO.class), HttpStatus.OK);
    }
}
