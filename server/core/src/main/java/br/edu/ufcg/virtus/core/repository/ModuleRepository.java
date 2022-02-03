package br.edu.ufcg.virtus.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.Module;

@Repository
public interface ModuleRepository extends CrudBaseRepository<Module, Integer> {

    List<Module> findByOrderByName();

    @Query(value = "SELECT DISTINCT(md.*) " +
            "FROM core.user_account ua " +
            "JOIN core.user_role ur on ua.id = ur.user_account_id " +
            "JOIN core.role r on ur.role_id = r.id " +
            "JOIN core.role_permission rp on rp.role_id = r.id " +
            "JOIN core.permission p on p.id = rp.permission_id " +
            "JOIN core.module md on md.id = p.module_id " +
            "WHERE ua.username = ?1 and visible=true", nativeQuery = true)
    List<Module> findAllPermited(String username);

    boolean existsByNameAndDeletedIsFalse(String name);

    boolean existsByNameAndDeletedIsFalseAndIdNot(String name, Integer id);

    boolean existsByLinkAndDeletedIsFalse(String link);

    boolean existsByLinkAndDeletedIsFalseAndIdNot(String link, Integer id);

    boolean existsByPathNameAndDeletedIsFalseAndPathNameNotNull(String pathName);

    boolean existsByPathNameAndDeletedIsFalseAndIdNotAndPathNameNotNull(String pathName, Integer id);

}
