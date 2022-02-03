package br.edu.ufcg.virtus.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.model.PasswordDefinitionToken;
import br.edu.ufcg.virtus.core.repository.PasswordDefinitionTokenRepository;

@Service
public class PasswordDefinitionTokenService extends CrudService<PasswordDefinitionToken, Integer> {

    @Autowired
    private PasswordDefinitionTokenRepository repository;

    @Override
    protected PasswordDefinitionTokenRepository getRepository() {
        return this.repository;
    }

    public PasswordDefinitionToken findByToken(String token) {
        return this.getRepository().findFirstByTokenAndUsedFalseAndDeletedFalse(token);
    }
}
