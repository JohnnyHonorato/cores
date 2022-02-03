package br.edu.ufcg.virtus.core.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.BaseService;
import br.edu.ufcg.virtus.common.util.TokenUtil;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.model.ContactInfo;
import br.edu.ufcg.virtus.core.model.Module;
import br.edu.ufcg.virtus.core.model.People;
import br.edu.ufcg.virtus.core.model.Permission;
import br.edu.ufcg.virtus.core.model.Role;
import br.edu.ufcg.virtus.core.model.User;
import br.edu.ufcg.virtus.core.repository.ContactInfoRepository;
import br.edu.ufcg.virtus.core.repository.ModuleRepository;
import br.edu.ufcg.virtus.core.repository.PeopleRepository;
import br.edu.ufcg.virtus.core.repository.UserRepository;

@Service
public class AccountService extends BaseService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    public List<Module> findAllModules(HttpServletRequest request) throws BusinessException {
        String username;
        try {
            username = this.tokenUtil.getUserName(request);
        } catch (final ParseException e) {
            throw new BusinessException(e.getMessage());
        }
        return this.moduleRepository.findAllPermited(username);
    }


    public Set<Permission> getPermissions(HttpServletRequest request) throws BusinessException {
        try {
            final String username = this.tokenUtil.getUserName(request);
            final User user = this.userRepository.findOneByUsername(username);
            if (user != null) {
                final Set<Permission> permissions = new HashSet<>();
                for (final Role r: user.getRoles()) {
                    permissions.addAll(r.getPermissions());
                }
                return permissions;
            } else {
                final User newUser = new User();

                final String name = this.tokenUtil.getName(request);
                newUser.setName(name);
                newUser.setUsername(username);
                newUser.getRoles().add(new Role(2));

                final User userDB = this.userRepository.save(newUser);
                final String email = this.tokenUtil.getEmail(request);
                if (StringUtils.trimToNull(email) != null) {
                    final People people = new People();
                    people.setName(name);
                    people.setDeleted(false);
                    people.setUser(userDB);
                    final People peopleDB = this.peopleRepository.save(people);

                    final ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setOwnerType(ContactInfoOwnerType.PEOPLE);
                    contactInfo.setOwnerId(peopleDB.getId());
                    contactInfo.setEmail(email);
                    contactInfo.setEmailTag("MAIN");
                    this.contactInfoRepository.save(contactInfo);
                }
                final Set<Permission> permissions = new HashSet<>();
                for (final Role r: userDB.getRoles()) {
                    permissions.addAll(r.getPermissions());
                }
                return permissions;
            }
        } catch (final ParseException e) {
            return new HashSet<>();
        }
    }
}
