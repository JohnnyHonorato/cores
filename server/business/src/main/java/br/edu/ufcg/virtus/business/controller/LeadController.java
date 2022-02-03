package br.edu.ufcg.virtus.business.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ufcg.virtus.business.dto.LeadDTO;
import br.edu.ufcg.virtus.business.service.LeadService;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.common.security.authorization.InsertPermission;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("leads")
@Authorization("lead")
@Api(value = "lead", tags = "lead-controller")
public class LeadController {

    @Autowired
    private LeadService service;

    @GetMapping
    @ApiOperation(value = "Get all leads")
    @ReadPermission
    public ResponseEntity<String> findAll(HttpServletRequest request) throws BusinessException {
        return this.service.search(request);
    }

    @GetMapping(params = { "filter" })
    @ApiOperation(value = "Find all leads filtered")
    @ReadPermission
    public ResponseEntity<String> findAll(HttpServletRequest request, @RequestParam("filter") String filter) throws BusinessException {
        return this.service.search(request, filter);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get specific lead")
    @ReadPermission
    public ResponseEntity<String> getOne(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.getOne(request, id);
    }

    @PostMapping
    @ApiOperation(value = "Insert an lead")
    @InsertPermission
    public ResponseEntity<String> insert(HttpServletRequest request, @RequestBody LeadDTO modelDTO) throws BusinessException {
        return this.service.insert(request, modelDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an lead")
    @UpdatePermission
    public ResponseEntity<String> update(HttpServletRequest request, @PathVariable Integer id, @RequestBody LeadDTO modelDTO)
            throws BusinessException {
        return this.service.update(request, id, modelDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an lead")
    @DeletePermission
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.delete(request, id);
    }

    @DeleteMapping("/{id}/organizations/{organizationId}")
    @ApiOperation(value = "Delete an organization")
    @DeletePermission
    public ResponseEntity<?> deleteOrganization(HttpServletRequest request, @PathVariable Integer id,
            @PathVariable Integer organizationId) throws BusinessException {
        this.service.deleteOrganization(request, id, organizationId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
