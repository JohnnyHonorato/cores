package br.edu.ufcg.virtus.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.business.model.GenericEntity;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;

@Repository
public interface CoreRepository extends CrudBaseRepository<GenericEntity, Integer> {

    @Query(value = "SELECT DISTINCT (p.name) FROM core.permission p "
            + "INNER JOIN core.role_permission rp ON rp.permission_id = p.id "
            + "INNER JOIN core.user_role ur ON ur.role_id = rp.role_id "
            + "INNER JOIN core.user_account ua ON ua.id = ur.user_account_id "
            + "WHERE ua.username=?1", nativeQuery = true)
    List<String> getAllPermissions(String username);

   
}