package br.edu.ufcg.virtus.core.service.validation;

import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.domain.ContactInfoType;
import br.edu.ufcg.virtus.core.model.ContactInfo;

public class ContactInfoValidationHelper {

    public static void checkForRepeatedContact(ContactInfo contact, List<ContactInfo> contacts) throws BusinessException {
        for (final ContactInfo compareContact : contacts) {
            if (contact != compareContact && contact.getContactInfoType() == compareContact.getContactInfoType()) {
                if (((contact.getId() != null || compareContact.getId() != null) &&
                        compareContact.getId() != contact.getId()) ||
                        (contact.getId() == null && compareContact.getId() == null)) {
                    compareContacts(contact, compareContact);
                }
            }
        }
    }

    private static void compareContacts(ContactInfo contact, ContactInfo compareContact) throws BusinessException {
        if (contact.getContactInfoType().equals(ContactInfoType.EMAIL)) {
            if (compareContact.getEmail() != null && compareContact.getEmailTag() != null) {
                if (compareContact.getEmailTag().equals(contact.getEmailTag()) &&
                        compareContact.getEmail().equals(contact.getEmail())) {
                    throw new BusinessException("contact-info.not.duplicate.email", HttpStatus.BAD_REQUEST);
                }
            }
        } else if (contact.getContactInfoType().equals(ContactInfoType.PHONE)) {
            if (compareContact.getPhone() != null || compareContact.getPhoneTag() != null) {
                if (compareContact.getPhoneTag().equals(contact.getPhoneTag()) &&
                        compareContact.getPhone().equals(contact.getPhone())) {
                    throw new BusinessException("contact-info.not.duplicate.phone", HttpStatus.BAD_REQUEST);
                }
            }
        }
    }

    public static void validateContactFields(ContactInfo contact) throws BusinessException {
        switch (contact.getContactInfoType()) {
            case EMAIL:
                validateEmailContact(contact);
                break;
            case PHONE:
                validatePhoneContact(contact);
                break;
            case ADDRESS:
                validateAddressContact(contact);
                break;
        }
    }

    private static void validateEmailContact(ContactInfo contact) throws BusinessException {
        if (Strings.isBlank(contact.getEmail())) {
            throw new BusinessException("contact-info.email.address.required");
        }
        if (Strings.isBlank(contact.getEmailTag())) {
            throw new BusinessException("contact-info.email.tag.required");
        }
    }

    private static void validatePhoneContact(ContactInfo contact) throws BusinessException {
    	if(Strings.isBlank(contact.getPhoneCountryCode()) && Strings.isBlank(contact.getPhone()) && Strings.isBlank(contact.getPhoneTag())) {
    		throw new BusinessException("contact-info.phone.at-least-one");
    	}
    	
    }

    private static void validateAddressContact(ContactInfo contact) throws BusinessException {
        if (Strings.isBlank(contact.getAddressCountry()) && Strings.isBlank(contact.getAddressState()) && Strings.isBlank(contact.getAddressCity()) && Strings.isBlank(contact.getAddressNeighborhood()) && contact.getContactDomain() == null && Strings.isBlank(contact.getAddressZipCode()) && Strings.isBlank(contact.getAddressStreet())) {
            throw new BusinessException("contact-info.address.at-least-one");
        }
    }
}
