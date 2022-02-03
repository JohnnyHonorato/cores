package br.edu.ufcg.virtus.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.tracker.dto.TransitionDTO;
import br.edu.ufcg.virtus.tracker.model.Transition;
import br.edu.ufcg.virtus.tracker.service.TransitionService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("tracker-models/{trackerModelId}/transitions")
@Authorization("transition")
@Api(value = "transition", tags = "transition-controller")
public class TransitionController extends CrudBaseController<Transition, Integer, TransitionDTO> {

    @Autowired
    private TransitionService service;

    @Override
    protected TransitionService getService() {
        return this.service;
    }
}
