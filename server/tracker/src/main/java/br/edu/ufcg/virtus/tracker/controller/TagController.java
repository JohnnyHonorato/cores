package br.edu.ufcg.virtus.tracker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.tracker.dto.TagDTO;
import br.edu.ufcg.virtus.tracker.model.Tag;
import br.edu.ufcg.virtus.tracker.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("tracker-models/{trackerModelId}/tags")
@Authorization("tag")
@Api(value = "tag", tags = "tag-controller")
public class TagController extends CrudBaseController<Tag, Integer, TagDTO> {

    @Autowired
    private TagService service;

    @Override
    protected TagService getService() {
        return this.service;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all tags by tracker model")
    public ResponseEntity<List<TagDTO>> findByTrackerModel(HttpServletRequest request) {
        final Integer trackerModelId = Integer.parseInt(this.getPathVariable(request, "trackerModelId"));
        final List<Tag> agreements = this.service.findAll(trackerModelId);
        return new ResponseEntity<>(this.toList(agreements, TagDTO.class), HttpStatus.OK);
    }

}
