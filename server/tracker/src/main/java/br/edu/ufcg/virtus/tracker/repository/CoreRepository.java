package br.edu.ufcg.virtus.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.model.GenericEntity;

@Repository
public interface CoreRepository extends CrudBaseRepository<GenericEntity, Integer> {

    @Query(value = "SELECT p.name FROM core.people p "
            + "JOIN core.user_account ua ON ua.id = p.user_id "
            + "WHERE ua.id = ?1", nativeQuery = true)
    String getPersonNameByUserId(Integer userId);
    
    @Query(value = "SELECT username FROM core.user_account WHERE id = ?1", nativeQuery = true)
    String getPersonUsernameById(Integer id);

    @Query(value = "SELECT DISTINCT (p.name) FROM core.permission p "
            + "INNER JOIN core.role_permission rp ON rp.permission_id = p.id "
            + "INNER JOIN core.user_role ur ON ur.role_id = rp.role_id "
            + "INNER JOIN core.user_account ua ON ua.id = ur.user_account_id "
            + "WHERE ua.username=?1", nativeQuery = true)
    List<String> getAllPermissions(String username);

    @Query(value = "SELECT p.name FROM core.people p "
            + "WHERE p.id = ?1", nativeQuery = true)
    String getPersonNameById(Integer personId);

}