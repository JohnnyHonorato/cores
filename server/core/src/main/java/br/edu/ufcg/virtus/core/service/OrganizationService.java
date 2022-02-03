package br.edu.ufcg.virtus.core.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.model.ContactInfo;
import br.edu.ufcg.virtus.core.model.Organization;
import br.edu.ufcg.virtus.core.model.PeopleOrganization;
import br.edu.ufcg.virtus.core.repository.BusinessRepository;
import br.edu.ufcg.virtus.core.repository.OrganizationRepository;

@Service
public class OrganizationService extends CrudService<Organization, Integer> {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private PeopleOrganizationService peopleOrgService;

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    protected OrganizationRepository getRepository() {
        return this.repository;
    }

    @Override
    public Organization insert(Organization model) throws BusinessException {
        final Organization organization = super.insert(model);
        this.saveContacts(model);
        this.saveLeads(model);
        return organization;
    }

    @Override
    public void update(Integer id, Organization model) throws BusinessException {
        super.update(id, model);
        this.saveContacts(model);
        this.removeLeads(model);
        this.saveLeads(model);
    }

    @Override
    public void delete(Integer organizationId) throws BusinessException {
        if (this.businessRepository.existsOpportunityForOrganization(organizationId)) {
            throw new BusinessException("organization.has.business-opportunity", HttpStatus.BAD_REQUEST);
        } else {
            super.delete(organizationId);
        }
    }

    @Override
    public Organization getOne(Integer id) throws BusinessException {
        final Optional<Organization> result = this.getRepository().findById(id);
        if (result.isPresent()) {
            final Organization organization = result.get();
            organization
            .setContacts(this.contactInfoService.getAllByOwnerIdAndOwnerType(organization.getId(), ContactInfoOwnerType.ORGANIZATION));
            organization
            .setPeopleOrganizations(
                    this.peopleOrgService.getAllByOrganizationId(organization.getId()));
            return organization;
        }
        return null;
    }

    @Override
    protected void validateInsert(Organization model) throws BusinessException {
        this.validateGovernmentCode(model);
        this.validateContacts(model.getContacts());
        this.validateLeads(model);
    }

    @Override
    protected void validateUpdate(Organization model) throws BusinessException {
        this.validateGovernmentCode(model);
        this.validateContacts(model.getContacts());
        this.validateLeads(model);
    }

    @Transactional
    public void deletePeople(Integer organizationId, Integer peopleId) {
        this.peopleOrgService.deleteByPeopleIdAndOrganizationId(peopleId, organizationId);
    }

    private void saveContacts(Organization model) throws BusinessException {
        if (model.getContacts() != null) {
            for (final ContactInfo ci : model.getContacts()) {
                if (ci != null) {
                    ci.setOwnerId(model.getId());
                    ci.setOwnerType(ContactInfoOwnerType.ORGANIZATION);
                    if (ci.getId() != null) {
                        this.contactInfoService.update(ci.getId(), ci);
                    } else {
                        this.contactInfoService.insert(ci);
                    }
                }
            }
            if (model.getId() != null) {
                final List<ContactInfo> existingContacts = this.contactInfoService.getAllByOwnerIdAndOwnerType(model.getId(), ContactInfoOwnerType.ORGANIZATION);
                final List<ContactInfo> deletedContacts =
                        existingContacts.stream()
                        .filter(existingContact -> existingContact.getId() != null &&
                        model.getContacts().stream().noneMatch(modelContact -> modelContact.getId() == existingContact.getId())
                                ).collect(Collectors.toList());
                for (final ContactInfo contact : deletedContacts) {
                    this.contactInfoService.delete(contact.getId());
                }
            }
        }
    }

    private void validateContacts(List<ContactInfo> contacts) throws BusinessException {
        this.contactInfoService.validateContacts(contacts);
    }

    private void saveLeads(Organization model) {
        if (model.getPeopleOrganizations() != null && !model.getPeopleOrganizations().isEmpty()) {
            for (final PeopleOrganization peopleOrganization : model.getPeopleOrganizations()) {
                peopleOrganization.setOrganization(new Organization(model.getId()));
                this.peopleService.savePeopleOrganization(peopleOrganization);
            }
        }
    }

    private void removeLeads(Organization model) {
        final Set<PeopleOrganization> previousPeopleOnOrganization = this.peopleOrgService.getAllByOrganizationId(model.getId());
        for (final PeopleOrganization prevPeople : previousPeopleOnOrganization) {
            if (!model.getPeopleOrganizations().stream()
                    .anyMatch(lead -> lead.getId().equals(prevPeople.getId()))) {
                this.peopleService.deletePeopleOrganizationById(prevPeople.getId());
            }
        }
    }

    private void validateLeads(Organization model) throws BusinessException {
        if (model.getPeopleOrganizations() != null && !model.getPeopleOrganizations().isEmpty()) {
            for (final PeopleOrganization peopleOrganization : model.getPeopleOrganizations()) {
                this.peopleService.validateGovernmentCode(peopleOrganization.getPeople());
                for (final PeopleOrganization comparedPeople : model.getPeopleOrganizations()) {
                    this.compareLeads(peopleOrganization, comparedPeople);
                }

            }
        }
    }

    private void compareLeads(PeopleOrganization peopleOrganization, PeopleOrganization comparedPeople) throws BusinessException {
        if (peopleOrganization != comparedPeople) {
            if (peopleOrganization.getDepartment().equals(comparedPeople.getDepartment()) &&
                    peopleOrganization.getPosition().equals(comparedPeople.getPosition()) &&
                    peopleOrganization.getPeople().getName().equals(comparedPeople.getPeople().getName())) {
                throw new BusinessException("lead.not.duplicate");
            }
        }

    }

    private void validateGovernmentCode(Organization model) throws BusinessException {
        if (Strings.isBlank(model.getGovernmentCode())) {
            throw new BusinessException("organization.government-code.required", HttpStatus.BAD_REQUEST);
        }

        boolean exists;
        if (model.getId() != null) {
            exists = this.repository.existsByGovernmentCodeAndDeletedIsFalseAndIdIsNot(model.getGovernmentCode(), model.getId());
        } else {
            exists = this.repository.existsByGovernmentCodeAndDeletedIsFalse(model.getGovernmentCode());
        }
        if (exists) {
            throw new BusinessException("organization.government-code.exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void validateDelete(Integer id) throws BusinessException {
        super.validateDelete(id);
        this.notAlreadyDeleted(id);
    }

    private void notAlreadyDeleted(Integer id) throws BusinessException {
        final Optional<Organization> org = this.getRepository().findById(id);
        if (!org.isPresent()) {
            throw new BusinessException("organization.delete.already-deleted", HttpStatus.BAD_REQUEST);
        }
    }

    public Organization getOrganizationByCNPJ(String cnpj) throws BusinessException {
        final Organization organization = this.repository.findOneByCnpj(cnpj);
        if (organization == null) {
            throw new BusinessException("organization.not.found", HttpStatus.BAD_REQUEST);
        }
        organization
        .setContacts(this.contactInfoService.getAllByOwnerIdAndOwnerType(organization.getId(), ContactInfoOwnerType.ORGANIZATION));
        return organization;
    }

}
