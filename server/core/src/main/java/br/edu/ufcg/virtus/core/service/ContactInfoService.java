package br.edu.ufcg.virtus.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.domain.ContactInfoOwnerType;
import br.edu.ufcg.virtus.core.model.ContactInfo;
import br.edu.ufcg.virtus.core.repository.ContactInfoRepository;
import br.edu.ufcg.virtus.core.service.validation.ContactInfoValidationHelper;

@Service
public class ContactInfoService extends CrudService<ContactInfo, Integer>{

    @Autowired
    private ContactInfoRepository repository;

    private static final Integer REQUIRED_MAIN_EMAIL_COUNT = 1;
    private static final Integer REQUIRED_MAIN_PHONE_COUNT = 1;

    @Override
    protected CrudBaseRepository<ContactInfo, Integer> getRepository() {
        return this.repository;
    }

    public List<ContactInfo> getAllByOwnerIdAndOwnerType(Integer id, ContactInfoOwnerType organizationType) {
        return this.repository.getAllByOwnerIdAndOwnerType(id, organizationType);
    }

    public ContactInfo getOneByMemberMainEmail(String institutionalEmail) {
        return this.repository.getOneByInstitutionalEmailAndMemberOwnerTypeAndNotDeleted(institutionalEmail);
    }

    @Override
    protected void validateDelete(Integer id) throws BusinessException {
        final ContactInfo contactInfoDB = this.repository.findById(id).get();
        if (contactInfoDB.getOwnerType().equals(ContactInfoOwnerType.PEOPLE)) {
            final Integer ownerId = contactInfoDB.getOwnerId();
            final Integer mainEmailCount = this.repository.countMainEmailByOwnerId(ownerId);
            if (mainEmailCount <= ContactInfoService.REQUIRED_MAIN_EMAIL_COUNT
                    && contactInfoDB.getEmailTag() != null &&
                    contactInfoDB.getEmailTag().equals("MAIN")) {
                throw new BusinessException("contact-info.one.main.email.delete");
            }

        }
    }

    protected void validateContacts(List<ContactInfo> contacts) throws BusinessException {
        if (contacts != null && !contacts.isEmpty()) {
            for (final ContactInfo contact : contacts) {
                ContactInfoValidationHelper.validateContactFields(contact);
                ContactInfoValidationHelper.checkForRepeatedContact(contact, contacts);
            }
            this.validateMainContact(contacts);
        }
    }

    private void validateMainContact(List<ContactInfo> contacts) throws BusinessException {
        int mainEmailCount = 0;
        int mainPhoneCount = 0;
        for (final ContactInfo contact : contacts) {
            if (contact.getEmail() != null && contact.getEmailTag().equals("MAIN")) {
                mainEmailCount++;
            }
            if (mainEmailCount > REQUIRED_MAIN_EMAIL_COUNT) {
                throw new BusinessException("contact-info.one.main.email", HttpStatus.BAD_REQUEST);
            }
            if (contact.getPhone() != null && contact.getPhoneTag().equals("MAIN")) {
                mainPhoneCount++;
            }
            if (mainPhoneCount > REQUIRED_MAIN_PHONE_COUNT) {
                throw new BusinessException("contact-info.one.main.phone", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
