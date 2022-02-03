package br.edu.ufcg.virtus.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.tracker.dto.AttributeDTO;
import br.edu.ufcg.virtus.tracker.model.Attribute;
import br.edu.ufcg.virtus.tracker.service.AttributeService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("tracker-models/{trackerModelId}/attribute")
@Authorization("attribute")
@Api(value = "attribute", tags = "attribute-controller")
public class AttributeController extends CrudBaseController<Attribute, Integer, AttributeDTO> {

	@Autowired
	private AttributeService service;

	@Override
	protected AttributeService getService() {
		return this.service;
	}

}
