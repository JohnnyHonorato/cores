package br.edu.ufcg.virtus.core.repository;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.User;

@Repository
public interface UserRepository extends CrudBaseRepository<User, Integer> {

    User findOneByUsername(String username);

    User findByIdAndDeletedIsFalse(Integer id);

    boolean existsByUsernameAndDeletedIsFalse(String userName);

    boolean existsByUsernameAndDeletedIsFalseAndIdNot(String username, Integer id);

}
