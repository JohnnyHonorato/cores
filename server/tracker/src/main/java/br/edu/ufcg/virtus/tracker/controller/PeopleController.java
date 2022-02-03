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
import br.edu.ufcg.virtus.tracker.service.PeopleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("people")
@Authorization("people")
@Api(value = "people", tags = "people-controller")
public class PeopleController extends BaseController {

    @Autowired
    private PeopleService service;

    @GetMapping(params = { "filter" })
    @ApiOperation(value = "Find all people filtered")
    @ReadPermission
    public ResponseEntity<String> findAll(HttpServletRequest request, @RequestParam("filter") String filter) throws BusinessException {
        return this.service.search(request, filter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find one people")
    @ReadPermission
    public ResponseEntity<String> findOne(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.getOne(request, id);
    }

    @GetMapping(value = "/module/{moduleId}", params = { "filter" })
    @ReadPermission
    public ResponseEntity<String> getAllByModule(HttpServletRequest request, @PathVariable Integer moduleId,
            @RequestParam("filter") String filter) throws BusinessException {
        return this.service.getAllByModule(request, moduleId, filter);
    }

    @GetMapping(value = "/organizations/{organizationId}")
    @ReadPermission
    public ResponseEntity<String> getAllByOrganization(HttpServletRequest request, @PathVariable Integer organizationId,
            @RequestParam("filter") String filter) throws BusinessException {
        return this.service.getAllByOrganization(request, organizationId, filter);
    }
}
