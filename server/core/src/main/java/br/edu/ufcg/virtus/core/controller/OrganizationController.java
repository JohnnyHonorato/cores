package br.edu.ufcg.virtus.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.core.dto.OrganizationDTO;
import br.edu.ufcg.virtus.core.dto.search.OrganizationSearchDTO;
import br.edu.ufcg.virtus.core.model.Organization;
import br.edu.ufcg.virtus.core.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("organizations")
@Authorization("organization")
@Api(value = "organization", tags = "organization-controller")
public class OrganizationController extends CrudBaseController<Organization, Integer, OrganizationDTO> {

    @Autowired
    private OrganizationService service;

    @Override
    protected OrganizationService getService() {
        return this.service;
    }

    @Override
    protected Class<OrganizationSearchDTO> getSearchDTOClass() {
        return OrganizationSearchDTO.class;
    }

    @DeleteMapping("/{id}/people/{peopleId}")
    @DeletePermission
    public ResponseEntity<?> deletePeople(HttpServletRequest request, @PathVariable Integer id,
            @PathVariable Integer peopleId) throws BusinessException {
        this.getService().deletePeople(id, peopleId);

        return this.success();
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    @ApiOperation(value = "Get a item with an CNPJ")
    public ResponseEntity<OrganizationDTO> getByCNPJ(HttpServletRequest request, @PathVariable String cnpj)
            throws BusinessException {
        final Organization organization = this.service.getOrganizationByCNPJ(cnpj);
        return new ResponseEntity<>(this.toDTO(organization), HttpStatus.OK);
    }

    @Override
    protected OrganizationDTO toDTO(Organization model) {
        if (model != null) {
            return super.toDTO(model);
        }
        return null;
    }

}
