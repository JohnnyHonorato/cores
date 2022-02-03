package br.edu.ufcg.virtus.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ufcg.virtus.common.controller.BaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.service.ContactInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("contacts-info")
@Api(value = "contact-info", tags = "contact-info-controller")
public class ContactInfoController extends BaseController {

    @Autowired
    private ContactInfoService service;

    @ApiOperation(value = "Delete an item")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(HttpServletRequest request, @PathVariable Integer id) throws BusinessException {
        this.service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
