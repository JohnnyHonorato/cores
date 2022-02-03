package br.edu.ufcg.virtus.core.service.validation;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.core.model.PeopleOrganization;

public class PeopleValidationHelper {

    public static void validatePeopleOrganization(PeopleOrganization organization) throws BusinessException {
        if (organization.getPeople() == null) {
            throw new BusinessException("people.organizations.people.required");
        }
        if (organization.getOrganization() == null) {
            throw new BusinessException("people.organizations.organization.required");
        }
    }

}
