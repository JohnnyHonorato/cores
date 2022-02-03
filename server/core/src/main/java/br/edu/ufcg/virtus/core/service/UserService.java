package br.edu.ufcg.virtus.core.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.dto.PasswordDTO;
import br.edu.ufcg.virtus.core.model.PasswordDefinitionToken;
import br.edu.ufcg.virtus.core.model.People;
import br.edu.ufcg.virtus.core.model.Role;
import br.edu.ufcg.virtus.core.model.User;
import br.edu.ufcg.virtus.core.repository.PeopleRepository;
import br.edu.ufcg.virtus.core.repository.UserRepository;
import br.edu.ufcg.virtus.core.util.AuthUtil;
import br.edu.ufcg.virtus.core.util.CryptoUtil;
import br.edu.ufcg.virtus.core.util.Mail;
import freemarker.template.TemplateException;

@Service
public class UserService extends CrudService<User, Integer> {

    private static final int VALIDITY_IN_DAYS = 2;

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordDefinitionTokenService passwordDefinitionTokenService;

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    protected UserRepository getRepository() {
        return this.repository;
    }

    @Override
    protected User preInsert(User model) throws BusinessException {
        model.getRoles().add(new Role(2));
        return super.preInsert(model);
    }

    @Override
    protected void validateInsert(User user) throws BusinessException {
        this.checkUserName(user);
    }

    @Override
    protected void validateUpdate(User user) throws BusinessException {
        this.checkUserName(user);
    }

    @Override
    protected void validateDelete(Integer id) throws BusinessException {
        final boolean hasPeopleAssociated = this.peopleRepository.existsByUserIdAndDeletedIsFalse(id);
        if (hasPeopleAssociated) {
            throw new BusinessException("user.people.associated");
        }
    }

    private void checkUserName(User user) throws BusinessException {

        boolean existsByUsername;
        if (user.getId() != null) {
            existsByUsername = this.repository.existsByUsernameAndDeletedIsFalseAndIdNot(user.getUsername(), user.getId());
        } else {
            existsByUsername = this.repository.existsByUsernameAndDeletedIsFalse(user.getUsername());
        }

        if (existsByUsername) {
            throw new BusinessException("user.username.exists");
        }
    }

    public void passwordCreationEmail(People people, String email, String message)
            throws BusinessException {

        try {
            Thread.sleep(1000);
            if (people == null) {
                throw new BusinessException("user.not.found", HttpStatus.NOT_FOUND);
            }

            final String token = AuthUtil.createRandomToken();
            final Date validity = DateUtils.addDays(new Date(), UserService.VALIDITY_IN_DAYS);
            final PasswordDefinitionToken passwordDefinitionToken = new PasswordDefinitionToken(token, validity, people.getUser());
            passwordDefinitionToken.setDeleted(false);
            passwordDefinitionToken.setUsed(false);
            this.passwordDefinitionTokenService.insert(passwordDefinitionToken);
            final Mail mail = new Mail();
            mail.setFrom(this.env.getProperty("spring.mail.username"));

            mail.setTo(email);
            mail.setSubject("Definição de senha");

            final Map<String, Object> model = new HashMap<>();

            model.put("name", people.getName());
            model.put("url", this.env.getProperty("client.url") + "/change-password?token=" + token);
            model.put("msg", message);
            mail.setModel(model);
            this.emailService.sendSimpleMessage(mail, "password_create.ftl");
        } catch (MessagingException | TemplateException | IOException | InterruptedException e) {
            throw new BusinessException("email.send.error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void changePassword(PasswordDTO modelDTO, String token) throws BusinessException {
        final PasswordDefinitionToken pwToken = this.passwordDefinitionTokenService.findByToken(token);

        final User user = this.repository.findByIdAndDeletedIsFalse(pwToken.getUser().getId());
        if (user == null) {
            throw new BusinessException("user.not.found", HttpStatus.NOT_FOUND);
        }

        this.validatePassowrd(modelDTO, pwToken);

        user.setPassword(CryptoUtil.hash(modelDTO.getPassword()));
        this.getRepository().save(user);

        pwToken.setUsed(true);
        this.passwordDefinitionTokenService.update(pwToken.getId(), pwToken);
    }

    private void validatePassowrd(PasswordDTO modelDTO, PasswordDefinitionToken pwToken) throws BusinessException {
        if (pwToken == null || pwToken.isExpired()) {
            throw new BusinessException("user.token.not.found.or.expired", HttpStatus.NOT_FOUND);
        }

        if (!modelDTO.getPassword().equals(modelDTO.getPasswordConfirmation())) {
            throw new BusinessException("user.password.and.password.confirmation.doesnt.match", HttpStatus.BAD_REQUEST);
        }

        if (modelDTO.getPassword().trim().equals("")) {
            throw new BusinessException("user.password.blank", HttpStatus.BAD_REQUEST);
        }
    }

}
