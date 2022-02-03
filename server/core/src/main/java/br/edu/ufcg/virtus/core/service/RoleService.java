package br.edu.ufcg.virtus.core.service;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.model.Role;
import br.edu.ufcg.virtus.core.repository.RoleRepository;

@Service
public class RoleService extends CrudService<Role, Integer> {

    @Autowired
    private RoleRepository repository;

    @Override
    protected CrudBaseRepository<Role, Integer> getRepository() {
        return this.repository;
    }
    
    private final int NAME_MAX_LENGTH = 255;

    @Override
    protected void validateInsert(Role model) throws BusinessException {
    	super.validateInsert(model);
    	this.validateRequiredFields(model);
        if (this.repository.existsByName(model.getName())) {
            throw new BusinessException("role.name.exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void validateUpdate(Role model) throws BusinessException {
    	super.validateUpdate(model);
    	this.validateRequiredFields(model);
        if (this.repository.existsByNameAndIdNot(model.getName(), model.getId())) {
            throw new BusinessException("role.name.exists", HttpStatus.BAD_REQUEST);
        }
    }

    public List<Role> findAll() {
        return this.repository.findByOrderByName();
    }
    
    private void validateRequiredFields(Role model) throws BusinessException {
    	if (Strings.isBlank(model.getName())) {
    		throw new BusinessException("role.name.required", HttpStatus.BAD_REQUEST);
    	}
    	if (model.getName() != null && model.getName().length() > NAME_MAX_LENGTH) {
    		throw new BusinessException("role.name.max-length", HttpStatus.BAD_REQUEST);
    	}
    }

}
