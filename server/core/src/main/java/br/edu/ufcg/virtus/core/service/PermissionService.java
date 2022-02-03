package br.edu.ufcg.virtus.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.model.Module;
import br.edu.ufcg.virtus.core.model.Permission;
import br.edu.ufcg.virtus.core.repository.PermissionRepository;

@Repository
public class PermissionService extends CrudService<Permission, Integer> {

	@Autowired
	private PermissionRepository repository;

	@Override
	protected PermissionRepository getRepository() {
		return this.repository;
	}

	public void deleteRolePermission(Integer id) {
		this.getRepository().deleteRolePermission(id);
	}

	public void deleteRolePermissionByModuleId(Integer moduleId) {
		this.getRepository().deleteRolePermissionByModuleId(moduleId);
	}

	public void deleteByModuleId(Integer moduleId) {
		this.getRepository().deleteByModuleId(moduleId);
	}

	public void savePermissions(Module module) throws BusinessException {
		if (module.getPermissions() != null && !module.getPermissions().isEmpty()) {
			for (Permission permission : module.getPermissions()) {
				permission.setModule(module);
				if (permission.getId() != null) {
					this.update(permission.getId(), permission);
				} else {
					this.insert(permission);
				}
			}
		}
	}

	@Override
	protected void validateInsert(Permission model) throws BusinessException {
		super.validateInsert(model);
		this.checkRequiredFields(model);
	}

	@Override
	protected void validateUpdate(Permission model) throws BusinessException {
		super.validateUpdate(model);
		this.checkRequiredFields(model);
	}

	private void checkRequiredFields(Permission permission) throws BusinessException {
		if (Strings.isNullOrEmpty(permission.getName())) {
			throw new BusinessException("permission.name.required");
		}
		if (Strings.isNullOrEmpty(permission.getLabel())) {
			throw new BusinessException("permission.label.required");
		}
		if (Strings.isNullOrEmpty(permission.getDescription())) {
			throw new BusinessException("permission.description.required");
		}
	}

}