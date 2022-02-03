package br.edu.ufcg.virtus.core.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.domain.PeopleType;
import br.edu.ufcg.virtus.core.model.ContactInfo;
import br.edu.ufcg.virtus.core.model.People;
import br.edu.ufcg.virtus.core.model.PeopleHistory;
import br.edu.ufcg.virtus.core.model.PeopleOrganization;
import br.edu.ufcg.virtus.core.model.Role;
import br.edu.ufcg.virtus.core.model.User;
import br.edu.ufcg.virtus.core.repository.PeopleHistoryRepository;
import br.edu.ufcg.virtus.core.repository.PeopleOrganizationRepository;
import br.edu.ufcg.virtus.core.repository.PeopleRepository;
import br.edu.ufcg.virtus.core.repository.UserRepository;

@Service
public class PeopleService extends CrudService<People, Integer> {

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private PeopleOrganizationRepository peopleOrgRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PeopleHistoryRepository peopleHistoryRepository;

    @Autowired
    private UserService userSevice;

    @Autowired
    private ContactInfoService contactService;

    @Override
    protected PeopleRepository getRepository() {
        return this.repository;
    }

    @Override
    public People insert(People model) throws BusinessException {
        this.saveUser(model);
        final People people = super.insert(model);
        this.saveContacts(model);
        this.saveOrganizations(model);
        this.savePeopleHistory(model);
        return people;
    }

    @Override
    public void update(Integer id, People model) throws BusinessException {
        this.saveUser(model);
        super.update(id, model);
        this.saveContacts(model);
        this.saveOrganizations(model);
    }

    @Override
    public People getOne(Integer id) throws BusinessException {
        final Optional<People> result = this.getRepository().findById(id);
        if (result.isPresent()) {
            final People people = result.get();
            people.setContacts(this.contactService.getAllByOwnerIdAndOwnerType(people.getId(), ContactInfoOwnerType.PEOPLE));
            return people;
        }
        return null;
    }

    @Override
    protected void validateUpdate(People model) throws BusinessException {
        super.validateUpdate(model);
        this.validateGovernmentCode(model);
        this.validateIfExistsContact(model);
        this.validateContacts(model.getContacts());
    }

    @Override
    protected void validateInsert(People model) throws BusinessException {
        super.validateInsert(model);
        this.validateGovernmentCode(model);
        this.validateIfExistsContact(model);
        this.validateContacts(model.getContacts());
    }

    private void validateContacts(List<ContactInfo> contacts) throws BusinessException {
        this.contactService.validateContacts(contacts);
    }

    @Transactional
    public void deteleOrganization(Integer peopleId, Integer organizationId) {
        this.peopleOrgRepository.deleteByPeopleIdAndOrganizationId(peopleId, organizationId);
    }

    private void savePeopleHistory(People model) {
        final PeopleHistory peopleHistory = new PeopleHistory();
        peopleHistory.setType(model.getPeopleType());
        final Timestamp timestamp = new Timestamp(new Date().getTime());
        peopleHistory.setCreatedAt(timestamp);
        peopleHistory.setPeople(model);
        peopleHistory.setModuleSource(this.getModuleName(model.getPeopleType()));
        this.peopleHistoryRepository.save(peopleHistory);
    }

    private String getModuleName(PeopleType peopleType) {
        switch (peopleType) {
            case COLABORATOR:
                return "Core";
            case LEAD:
                return "Business";
            default:
                return "Core";
        }
    }

    private void saveUser(People model) throws BusinessException {
        if (model.getUser() != null) {
            final User newUser = model.getUser();
            if (newUser.getId() == null) {
                newUser.setName(model.getName());
                newUser.setExternal(true);
                for (final Role role : model.getUser().getRoles()) {
                    newUser.getRoles().add(role);
                }
                this.userSevice.insert(newUser);
                this.userSevice.passwordCreationEmail(model, this.getMainEmail(model), "Mensagem");
            } else {
                final User savedUser = this.userRepository.findByIdAndDeletedIsFalse(newUser.getId());
                for (final Role role : model.getUser().getRoles()) {
                    newUser.getRoles().add(role);
                }
                newUser.setExternal(savedUser.isExternal());
                this.userSevice.update(newUser.getId(), newUser);
            }
        }
    }

    private void saveContacts(People model) throws BusinessException {
        if (model.getContacts() != null) {
            for (final ContactInfo contact : model.getContacts()) {
                if (contact != null) {
                    contact.setOwnerId(model.getId());
                    contact.setOwnerType(ContactInfoOwnerType.PEOPLE);
                    if (contact.getId() == null) {
                        this.contactService.insert(contact);
                    } else {
                        this.contactService.update(contact.getId(), contact);
                    }
                }
            }
            if (model.getId() != null) {
                final List<ContactInfo> existingContacts = this.contactService.getAllByOwnerIdAndOwnerType(model.getId(),
                        ContactInfoOwnerType.PEOPLE);
                final List<ContactInfo> deletedContacts = existingContacts.stream()
                        .filter(existingContact -> (existingContact.getId() != null) &&
                                model.getContacts().stream().noneMatch(modelContact -> modelContact.getId() == existingContact.getId()))
                        .collect(Collectors.toList());
                for (final ContactInfo contact : deletedContacts) {
                    this.contactService.delete(contact.getId());
                }
            }
        }
    }

    private void saveOrganizations(People model) throws BusinessException {
        if ((model.getPeopleOrganizations() != null) && !model.getPeopleOrganizations().isEmpty()) {
            for (final PeopleOrganization organization : model.getPeopleOrganizations()) {
                organization.setPeople(new People(model.getId()));
                for (final PeopleOrganization comparedPeople : model.getPeopleOrganizations()) {
                    this.compareOrganizations(organization, comparedPeople);
                }
                this.peopleOrgRepository.save(organization);
            }
        }
    }

    private void compareOrganizations(PeopleOrganization peopleOrganization, PeopleOrganization comparedPeople) throws BusinessException {
        if (peopleOrganization != comparedPeople) {
            if (peopleOrganization.getDepartment().equals(comparedPeople.getDepartment()) &&
                    peopleOrganization.getPosition().equals(comparedPeople.getPosition()) &&
                    peopleOrganization.getOrganization().getFantasyName().equals(comparedPeople.getOrganization().getFantasyName())) {
                throw new BusinessException("organization.not.duplicate");
            }
        }

    }

    public void validateGovernmentCode(People model) throws BusinessException {
        if ((model == null) || Strings.isBlank(model.getGovernmentCode())) {
            return;
        }

        boolean exists;
        if (model.getId() != null) {
            exists = this.repository.existsByGovernmentCodeAndDeletedIsFalseAndIdIsNot(model.getGovernmentCode(), model.getId());
        } else {
            exists = this.repository.existsByGovernmentCodeAndDeletedIsFalse(model.getGovernmentCode());
        }
        if (exists) {
            throw new BusinessException("people.government-code.exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateIfExistsContact(People model) throws BusinessException {
        if (model.getPeopleType().equals(PeopleType.COLABORATOR)) {
            boolean hasEmail = false;
            if ((model.getContacts() != null) && !model.getContacts().isEmpty()) {
                for (final ContactInfo contact : model.getContacts()) {
                    if (contact.getEmail() != null) {
                        hasEmail = true;
                        break;
                    }
                }
            }
            if (!hasEmail) {
                throw new BusinessException("people.contact-info.required", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private String getMainEmail(People model) throws BusinessException {
        for (final ContactInfo contactInfo : model.getContacts()) {
            if (contactInfo.getEmailTag().equals("MAIN")) {
                return contactInfo.getEmail();
            }
        }

        throw new BusinessException("people.no-main-email", HttpStatus.BAD_REQUEST);
    }

    public List<People> getAllPeopleByOrganizationId(Integer organizationId) {
        return this.repository.getAllByPeopleOrganizations_OrganizationId(organizationId);
    }

    public void savePeopleOrganization(PeopleOrganization peopleOrganization) {
        this.peopleOrgRepository.save(peopleOrganization);
    }

    public void deleteByPeopleIdAndOrganizationId(Integer peopleId, Integer organizationId) {
        this.peopleOrgRepository.deleteByPeopleIdAndOrganizationId(peopleId, organizationId);
    }
    
    public void deletePeopleOrganizationById(Integer peopleId) {
        this.peopleOrgRepository.deleteById(peopleId);
    }

    public PageListDTO searchAllByModulePermission(Integer moduleId, SearchFilterDTO filter) {
        final PageListDTO result = this.repository.searchByModule(moduleId, filter);
        return result;
    }

    public PageListDTO searchAllByOrganization(Integer organizationId, SearchFilterDTO filter) {
        final PageListDTO result = this.repository.searchByOrganization(organizationId, filter);
        return result;
    }

}
