package br.edu.ufcg.virtus.core.repository;

import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.model.PasswordDefinitionToken;


@Repository
public interface PasswordDefinitionTokenRepository extends CrudBaseRepository<PasswordDefinitionToken, Integer> {

    PasswordDefinitionToken findFirstByTokenAndUsedFalseAndDeletedFalse(String token);
}
