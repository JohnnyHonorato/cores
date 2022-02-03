package br.edu.ufcg.virtus.tracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.BaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.tracker.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("organizations")
@Authorization("organization")
@Api(value = "organization", tags = "organization-controller")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService service;

    @GetMapping(params = { "filter" })
    @ApiOperation(value = "Find all organizations filtered")
    @ReadPermission
    public ResponseEntity<String> findAll(HttpServletRequest request, @RequestParam("filter") String filter) throws BusinessException {
        return this.service.search(request, filter);
    }

    @ApiOperation(value = "Get an Organization with an ID")
    @GetMapping("/{id}")
    @ReadPermission
    public ResponseEntity<String> getOne(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.getOne(request, id);
    }

}
