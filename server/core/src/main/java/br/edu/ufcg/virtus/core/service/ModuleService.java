package br.edu.ufcg.virtus.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.common.util.ValidationUtil;
import br.edu.ufcg.virtus.core.model.Module;
import br.edu.ufcg.virtus.core.repository.ModuleRepository;

@Service
public class ModuleService extends CrudService<Module, Integer> {

	@Autowired
	private ModuleRepository repository;

	@Autowired
	private PermissionService permissionService;

	@Override
	protected CrudBaseRepository<Module, Integer> getRepository() {
		return this.repository;
	}

	public List<Module> findAll() {
		return this.repository.findByOrderByName();
	}

	@Override
	public Module insert(Module module) throws BusinessException {
		final Module moduleDB = super.insert(module);
		this.savePermissions(moduleDB);
		return moduleDB;
	}

	@Override
	public void update(Integer id, Module module) throws BusinessException {
		super.update(id, module);
		this.savePermissions(module);
	}

	private void checkModuleFields(Module module) throws BusinessException {
		boolean existsByName;
		boolean existsByLink;
		boolean existsByPathName;

		if (module.getId() != null) {
			existsByName = this.repository.existsByNameAndDeletedIsFalseAndIdNot(module.getName(), module.getId());
			existsByLink = this.repository.existsByLinkAndDeletedIsFalseAndIdNot(module.getLink(), module.getId());
			existsByPathName = this.repository
					.existsByPathNameAndDeletedIsFalseAndIdNotAndPathNameNotNull(module.getPathName(), module.getId());
		} else {
			existsByName = this.repository.existsByNameAndDeletedIsFalse(module.getName());
			existsByLink = this.repository.existsByLinkAndDeletedIsFalse(module.getLink());
			existsByPathName = this.repository
					.existsByPathNameAndDeletedIsFalseAndPathNameNotNull(module.getPathName());
		}

		if (existsByName) {
			throw new BusinessException("module.name.exists");
		} else if (existsByLink) {
			throw new BusinessException("module.link.exists");
		} else if (existsByPathName) {
			throw new BusinessException("module.path.exists");
		}
	}

	@Override
	protected void validateInsert(Module module) throws BusinessException {
		super.validateInsert(module);
		ValidationUtil.validatePathField(module.getOpenMode().toString(), module.getPathName());
		this.checkModuleFields(module);
	}

	@Override
	protected void validateUpdate(Module module) throws BusinessException {
		super.validateUpdate(module);
		ValidationUtil.validatePathField(module.getOpenMode().toString(), module.getPathName());
		this.checkModuleFields(module);
	}

	@Override
	protected void preDelete(Integer id) throws BusinessException {
		this.permissionService.deleteRolePermissionByModuleId(id);
		this.permissionService.deleteByModuleId(id);
	}

	private void savePermissions(Module module) throws BusinessException {
		this.permissionService.savePermissions(module);
	}

}
