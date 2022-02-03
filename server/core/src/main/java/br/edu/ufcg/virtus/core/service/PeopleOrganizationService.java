package br.edu.ufcg.virtus.core.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.core.model.PeopleOrganization;
import br.edu.ufcg.virtus.core.repository.PeopleOrganizationRepository;

@Service
public class PeopleOrganizationService extends CrudService<PeopleOrganization, Integer> {

    @Autowired
    PeopleOrganizationRepository repository;

    @Override
    protected CrudBaseRepository<PeopleOrganization, Integer> getRepository() {
        return this.repository;
    }

    public Set<PeopleOrganization> getAllByOrganizationId(Integer organizationId) {
        return this.repository.getAllByOrganizationId(organizationId);
    }

    public void deleteByPeopleIdAndOrganizationId(Integer peopleId, Integer organizationId) {
        this.repository.deleteByPeopleIdAndOrganizationId(peopleId, organizationId);
    }

}
