package br.edu.ufcg.virtus.tracker.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import br.edu.ufcg.virtus.tracker.dto.ChecklistDTO;
import br.edu.ufcg.virtus.tracker.model.Checklist;
import br.edu.ufcg.virtus.tracker.model.ChecklistItem;
import br.edu.ufcg.virtus.tracker.service.ChecklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("checklists")
@Authorization("checklist")
@Api(value = "checklist", tags = "checklist-controller")
public class ChecklistController extends CrudBaseController<Checklist, Integer, ChecklistDTO> {

    @Autowired
    private ChecklistService service;
    

    @Override
    protected ChecklistService getService() {
        return this.service;
    }
    
    @DeleteMapping("/{checklistId}/item/{checklistItemId}")
    @DeletePermission
    public ResponseEntity<ChecklistDTO> deleteChecklistItem(HttpServletRequest request, @PathVariable Integer checklistItemId)
            throws BusinessException {
        this.service.deleteChecklistItem(checklistItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiOperation(value = "Update a status of a checklist item")
    @PutMapping("/{checklistId}/item/{checklistItemId}")
    @UpdatePermission
    public ResponseEntity<?> updateChecklistItem(HttpServletRequest request, @RequestBody ChecklistItem checklistItem)
            throws BusinessException {
        this.getService().updateValueChecklistItem(checklistItem);
        return this.success();
    }
    
  


}
