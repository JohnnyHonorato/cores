package br.edu.ufcg.virtus.tracker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.common.security.authorization.ReadPermission;
import br.edu.ufcg.virtus.tracker.dto.StatusDTO;
import br.edu.ufcg.virtus.tracker.dto.TrackerModelDTO;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import br.edu.ufcg.virtus.tracker.dto.search.TrackerModelSearchDTO;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;
import br.edu.ufcg.virtus.tracker.service.TrackerModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("tracker-models")
@Authorization("tracker-model")
@Api(value = "tracker-model", tags = "tracker-model-controller")
public class TrackerModelController extends CrudBaseController<TrackerModel, Integer, TrackerModelDTO> {

    @Autowired
    private TrackerModelService service;

    @Override
    protected TrackerModelService getService() {
        return this.service;
    }

    @Override
    protected Class<TrackerModelSearchDTO> getSearchDTOClass() {
        return TrackerModelSearchDTO.class;
    }

    @Override
    @ApiOperation(value = "Get a item with an ID")
    @GetMapping("/{id}")
    @ReadPermission
    public TrackerModelDTO getOne(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        final TrackerModel model = this.getOneModel(id);
        final TrackerModelDTO result = this.toDTO(model);
        final List<StatusDTO> status = this.toList(model.getStatus(), StatusDTO.class);
        final List<TransitionDTO> transitions = this.toList(model.getTransitions(), TransitionDTO.class);
        result.setTransitions(transitions);
        result.setStatus(status);
        return result;
    }

    @GetMapping("/all")
    @ReadPermission
    public ResponseEntity<List<TrackerModelDTO>> findAll(HttpServletRequest request) {
        final List<TrackerModel> trackerModels = this.service.findAll();
        return new ResponseEntity<>(this.toList(trackerModels, TrackerModelDTO.class), HttpStatus.OK);
    }
    
    @GetMapping("/module/{moduleId}")
    @ReadPermission
    public ResponseEntity<List<TrackerModelDTO>> getAllByModule(HttpServletRequest request, @PathVariable Integer moduleId) {
        final List<TrackerModel> trackerModels = this.service.findAllByModule(moduleId);
        return new ResponseEntity<>(this.toList(trackerModels, TrackerModelDTO.class), HttpStatus.OK);
    }

}
