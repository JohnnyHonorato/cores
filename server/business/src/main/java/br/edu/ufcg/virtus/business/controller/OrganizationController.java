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

import br.edu.ufcg.virtus.business.dto.OrganizationDTO;
import br.edu.ufcg.virtus.business.service.OrganizationService;
import br.edu.ufcg.virtus.common.controller.BaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.common.security.authorization.InsertPermission;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("organizations")
@Authorization("organization")
@Api(value = "organization", tags = "organization-controller")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService service;

    @GetMapping
    @ApiOperation(value = "Get all organizations")
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
    @ApiOperation(value = "Get specific organization")
    @ReadPermission
    public ResponseEntity<String> getOne(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.getOne(request, id);
    }

    @GetMapping("/cnpj/{cnpj}")
    @ApiOperation(value = "Get specific organization by cnpj")
    public ResponseEntity<String> getByCnpj(HttpServletRequest request, @PathVariable String cnpj) throws BusinessException {
        return this.service.getOrganizationByCNPJ(request, cnpj);
    }

    @PostMapping
    @ApiOperation(value = "Insert an organization")
    @InsertPermission
    public ResponseEntity<String> insert(HttpServletRequest request, @RequestBody OrganizationDTO modelDTO) throws BusinessException {
        return this.service.insert(request, modelDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an organization")
    @UpdatePermission
    public ResponseEntity<String> update(HttpServletRequest request, @PathVariable Integer id, @RequestBody OrganizationDTO modelDTO)
            throws BusinessException {
        return this.service.update(request, id, modelDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an organization")
    @DeletePermission
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        return this.service.delete(request, id);
    }

    @DeleteMapping("/contacts-info/{contactInfoId}")
    @ApiOperation(value = "Delete an contact info")
    public ResponseEntity<?> deleteContactInfo(HttpServletRequest request, @PathVariable Integer contactInfoId) throws BusinessException {
        this.service.deleteContactInfo(request, contactInfoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/leads/{leadId}")
    @ApiOperation(value = "Delete an organization")
    @DeletePermission
    public ResponseEntity<?> deleteLead(HttpServletRequest request, @PathVariable Integer id,
            @PathVariable Integer leadId) throws BusinessException {
        this.service.deleteLead(request, id, leadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
