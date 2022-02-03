package br.edu.ufcg.virtus.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.common.util.JSonUtil;
import br.edu.ufcg.virtus.core.dto.PeopleDTO;
import br.edu.ufcg.virtus.core.model.People;
import br.edu.ufcg.virtus.core.service.PeopleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("people")
@Authorization("people")
@Api(value = "people", tags = "people-controller")
public class PeopleController extends CrudBaseController<People, Integer, PeopleDTO> {

    @Autowired
    private PeopleService service;

    @Override
    protected PeopleService getService() {
        return this.service;
    }

    @DeleteMapping("/{id}/organizations/{organizationId}")
    @DeletePermission
    public ResponseEntity<?> deleteOrganization(HttpServletRequest request, @PathVariable Integer id,
            @PathVariable Integer organizationId) throws BusinessException {
        this.getService().deteleOrganization(id, organizationId);

        return this.success();
    }

    @GetMapping(value = "/module/{moduleId}", params = { "filter" })
    @ReadPermission
    public ResponseEntity<ModelDTO> getAllByModule(HttpServletRequest request, @PathVariable Integer moduleId,
            @RequestParam(value = "filter", required = false) String filterJSon) {
        final SearchFilterDTO filter = JSonUtil.fromJSon(filterJSon, SearchFilterDTO.class);
        final PageListDTO people = this.getService().searchAllByModulePermission(moduleId, filter);
        people.setItems(this.toSearchListDTO(people.getItems()));
        return new ResponseEntity<>(people, HttpStatus.OK);

    }

    /**
     * Get the List of People of a Organization.
     *
     * @return DTO with list of model found.
     */
    @ApiOperation(value = "View a list of available items")
    @GetMapping(path = "/organizations/{organizationId}")
    @ReadPermission
    public List<PeopleDTO> getAllByOrganizationId(HttpServletRequest request, @PathVariable Integer organizationId) {
        return this.toListDTO(this.service.getAllPeopleByOrganizationId(organizationId));
    }

    /**
     * Search the List of People of a Organization.
     *
     * @return DTO with list of model found and filtered.
     */
    @ApiOperation(value = "View a list of available items")
    @GetMapping(path = "/organizations/{organizationId}", params = { "filter" })
    @ReadPermission
    public ResponseEntity<ModelDTO> findAllByOrganizationId(HttpServletRequest request, @PathVariable Integer organizationId,
            @RequestParam(value = "filter", required = false) String filterJSon) {
        final SearchFilterDTO filter = JSonUtil.fromJSon(filterJSon, SearchFilterDTO.class);
        final PageListDTO people = this.getService().searchAllByOrganization(organizationId, filter);
        people.setItems(this.toSearchListDTO(people.getItems()));
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @Override
    protected PeopleDTO toDTO(People model) {
        if (model != null) {
            return super.toDTO(model);
        }
        return null;
    }
}
