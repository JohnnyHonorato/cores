package br.edu.ufcg.virtus.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.Permission;

@Repository
public interface PermissionRepository extends CrudBaseRepository<Permission, Integer> {

    @Modifying
    @Query(value = "DELETE FROM core.role_permission rp WHERE rp.permission_id = ?1", nativeQuery = true)
    void deleteRolePermission(Integer id);

    @Modifying
    @Query(value = "DELETE FROM core.role_permission rp "
            + "WHERE rp.permission_id IN (SELECT p.id FROM permission p WHERE p.module_id = ?1)", nativeQuery = true)
    void deleteRolePermissionByModuleId(Integer moduleId);

    @Modifying
    @Query(value = "DELETE FROM core.permission p WHERE p.module_id = ?1", nativeQuery = true)
    void deleteByModuleId(Integer moduleId);
    
    List<Permission> findByModuleId(Integer moduleId);
}