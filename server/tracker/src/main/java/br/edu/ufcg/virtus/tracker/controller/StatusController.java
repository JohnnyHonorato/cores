package br.edu.ufcg.virtus.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.tracker.dto.StatusDTO;
import br.edu.ufcg.virtus.tracker.model.Status;
import br.edu.ufcg.virtus.tracker.service.StatusService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("tracker-models/{trackerModelId}/status")
@Authorization("status")
@Api(value = "status", tags = "status-controller")
public class StatusController extends CrudBaseController<Status, Integer, StatusDTO> {

    @Autowired
    private StatusService service;

    @Override
    protected StatusService getService() {
        return this.service;
    }

}