package br.edu.ufcg.virtus.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.Role;

@Repository
public interface RoleRepository extends CrudBaseRepository<Role, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    List<Role> findByOrderByName();

}
