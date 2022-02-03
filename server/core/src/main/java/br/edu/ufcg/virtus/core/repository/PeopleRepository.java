package br.edu.ufcg.virtus.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.repository.Condition;
import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.core.domain.PeopleType;
import br.edu.ufcg.virtus.core.model.People;
import br.edu.ufcg.virtus.core.model.PeopleHistory;

@Repository
public interface PeopleRepository extends CrudBaseRepository<People, Integer> {

    boolean existsByUserIdAndDeletedIsFalse(Integer id);

    boolean existsByGovernmentCodeAndDeletedIsFalse(String governmentCode);

    boolean existsByGovernmentCodeAndDeletedIsFalseAndIdIsNot(String governmentCode, Integer id);

    @Override
    default Specification<People> specification(List<Condition> conditions, final String input) {
        return (people, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            conditions.forEach(condition -> {
                final Join<People, PeopleHistory> peopleHistory = people.join("history");
                final PeopleType type = this.getValue(condition.getValue().toString());
                predicates.add(builder.equal(peopleHistory.get("type"), type));
            });

            if (!input.isEmpty()) {
                predicates.add(builder.like(builder.lower(people.get("name")), this.likeValue(input.toLowerCase())));
            }

            query.distinct(true);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    default PeopleType getValue(String type) {
        switch (type) {
            case "LEAD":
                return PeopleType.LEAD;
        }
        return null;
    }

    @Query(value = "SELECT pe.* FROM people pe "
            + "JOIN user_account ua ON ua.id = pe.user_id "
            + "JOIN user_role ur ON ur.user_account_id = ua.id "
            + "JOIN role_permission rp ON rp.role_id = ur.role_id "
            + "JOIN permission p ON p.id = rp.permission_id "
            + "WHERE p.module_id = ?1 "
            + "AND unaccent(pe.name) LIKE unaccent(?2) "
            + "GROUP BY pe.id", countQuery = "SELECT pe.* FROM people pe "
                    + "JOIN user_account ua ON ua.id = pe.user_id "
                    + "JOIN user_role ur ON ur.user_account_id = ua.id "
                    + "JOIN role_permission rp ON rp.role_id = ur.role_id "
                    + "JOIN permission p ON p.id = rp.permission_id "
                    + "WHERE p.module_id = ?1 "
                    + "AND unaccent(pe.name) LIKE unaccent(?2) "
                    + "GROUP BY pe.id", nativeQuery = true)
    Page<People> searchByModuleId(Integer moduleId, String search, Pageable pageable);

    default PageListDTO searchByModule(Integer moduleId, SearchFilterDTO filter) {
        final Page<People> result = this.searchByModuleId(moduleId, this.likeValue(filter.getSearch()), this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }

    List<People> getAllByPeopleOrganizations_OrganizationId(Integer organizationId);

    @Query("SELECT p FROM People p JOIN PeopleOrganization po ON po.people.id = p.id WHERE unaccent(p.name) like unaccent(:name) AND po.organization.id = :organizationId")
    Page<People> searchByNameAndOrganization(String name, Integer organizationId, Pageable pageable);

    default PageListDTO searchByOrganization(@Param("organizationId") Integer organizationId, SearchFilterDTO filter) {
        final Page<People> result = this.searchByNameAndOrganization(this.likeValue(filter.getSearch()), organizationId, this.page(filter));
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }

}
