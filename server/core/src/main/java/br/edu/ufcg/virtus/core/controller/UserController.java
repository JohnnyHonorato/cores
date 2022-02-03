package br.edu.ufcg.virtus.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufcg.virtus.common.controller.CrudBaseController;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.security.authorization.Authorization;
import br.edu.ufcg.virtus.core.dto.PasswordDTO;
import br.edu.ufcg.virtus.core.dto.UserDTO;
import br.edu.ufcg.virtus.core.model.User;
import br.edu.ufcg.virtus.core.service.UserService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("users")
@Authorization("user")
@Api(value = "user", tags = "user-controller")
public class UserController extends CrudBaseController<User, Integer, UserDTO> {

    @Autowired
    private UserService service;

    @Override
    protected UserService getService() {
        return this.service;
    }

    @Override
    public ResponseEntity<UserDTO> insert(HttpServletRequest request, @Valid UserDTO modelDTO) throws BusinessException {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    // TODO
    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestHeader(value = "token", required = false) String token, @RequestBody PasswordDTO modelDTO)
            throws BusinessException {
        this.service.changePassword(modelDTO, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
