package br.edu.ufcg.virtus.common.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.common.repository.Condition.ConditionBuilder;

/**
 * Base Repository for Search operations.
 *
 * @param <M>
 *            Model type.
 * @param <T>
 *            ID type.
 * @author Virtus
 */
@NoRepositoryBean
public interface SearchBaseRepository<M extends Model<T>, T extends Serializable> extends PagingAndSortingRepository<M, Serializable> {

    /* QUERIES */

    /**
     * Find all items with the specification criterias.
     *
     * @param spec
     *            Specification criterias.
     * @param pageable
     *            Pagination.
     * @return Page with items found.
     */
    Page<M> findAll(Specification<M> spec, Pageable pageable);

    /**
     * Searches entities with the filter and paginated.
     *
     * @param searchFilter
     *            Filter.
     * @return Result.
     */
    default PageListDTO search(SearchFilterDTO searchFilter) {
        final Page<M> result = this.searchFilter(searchFilter, new ArrayList<>());
        return new PageListDTO(result.getTotalPages(), result.getContent());
    }

    /**
     * Searches with conditions specified.
     *
     * @param searchFilter
     *            Filter
     * @param conditions
     *            Conditions.
     * @return Pageable result.
     */
    default Page<M> searchFilter(SearchFilterDTO searchFilter, List<Condition> conditions) {
        conditions = this.addConditions(searchFilter, conditions);

        return this.findAll(this.specification(conditions, searchFilter.getSearch()), this.page(searchFilter));
    }

    /* OBJECT MAPPING */

    /**
     * Extract the query result and puts into a list of the elements instances. <br>
     * The fields should be in the exactly same order as the result values.
     *
     * @param clazz
     *            Class of Element.
     * @param result
     *            Result.
     * @param fields
     *            Fields to be filled in order.
     * @return List of elements instances.
     */
    default <E> List<E> fromResult(Class<E> clazz, Iterable<Object[]> result, String[] fields) {
        final List<E> list = new ArrayList<>();

        for (final Object[] obj : result) {
            list.add(this.makeObject(clazz, obj, fields));
        }
        return list;
    }

    /**
     * Instantiate an object of the element type filled with the values informed.
     *
     * @param clazz
     *            Class of Element.
     * @param values
     *            Values.
     * @param fields
     *            Fields to be filled in order.
     * @return Instance of element filled.
     */
    default <E> E makeObject(Class<E> clazz, Object[] values, String[] fields) {
        try {
            final E item = ConstructorUtils.invokeConstructor(clazz, null);

            int index = 0;
            for (final String field : fields) {
                BeanUtils.setProperty(item, field, values[index++]);
            }
            return item;
        } catch (final Exception e) {
            return null;
        }
    }

    /* SEARCH API */

    /**
     * Build a page request to search items.
     *
     * @param searchFilter
     *            Search filter.
     * @return Page Request.
     */
    default PageRequest page(SearchFilterDTO searchFilter) {
        if (searchFilter.getSort() == null || searchFilter.getSort().getColumn().isEmpty()) {
            return this.page(searchFilter.getCurrentPage(), searchFilter.getPageSize());
        }

        return this.page(searchFilter.getCurrentPage(), searchFilter.getPageSize(), this.direction(searchFilter.getSort().getOrder()),
                searchFilter.getSort().getColumn());
    }

    /**
     * Build a page request to search items.
     *
     * @param currentPage
     *            Current Page.
     * @param pageSize
     *            Page Size.
     * @return Page Request.
     */
    default PageRequest page(Integer currentPage, Integer pageSize) {
        return PageRequest.of(currentPage - 1, pageSize);
    }

    /**
     * Build a page request to search items.
     *
     * @param currentPage
     *            Current page.
     * @param pageSize
     *            Page size.
     * @param direction
     *            Sort direction.
     * @param columns
     *            Columsn to sort.
     * @return Page Request.
     */
    default PageRequest page(Integer currentPage, Integer pageSize, Direction direction, String... columns) {
        return PageRequest.of(currentPage - 1, pageSize, direction, columns);
    }

    /**
     * Gets the sort direction.
     *
     * @param value
     *            Value as 'Desc' or 'Asc'.
     * @return Sort Direction.
     */
    default Direction direction(String value) {
        return value.isEmpty() ? Sort.Direction.ASC : Sort.Direction.fromString(value);
    }

    /**
     * Add the conditions based on the search filters.
     *
     * @param searchFilter
     *            Search Filters.
     * @param conditions
     *            Conditions.
     * @return List with the conditions.
     */
    default List<Condition> addConditions(SearchFilterDTO searchFilter, List<Condition> conditions) {
        final ConditionBuilder condBuilder = new ConditionBuilder();

        searchFilter.getFilters().forEach(filter -> conditions.add(
                condBuilder.field(filter.getField()).is(filter.getComparison()).value(filter.getValue()).build()));

        return conditions;
    }
    
    /**
     * Builds a specification with the conditions and filtering the input text.
     *
     * @param conditions
     *            Conditions.
     * @param input
     *            Input to filter.
     * @return Specification.
     */
    default Specification<M> specification(List<Condition> conditions, final String input) {
        return new SearchSpecification<>(conditions, input);
    }

    /**
     * Format the value for a like operation. <br>
     * Ex: %VALUE%.
     *
     * @param value
     *            Value.
     * @return Value formatted for a like operation.
     */
    default String likeValue(String value) {
        return String.format("%%%s%%", value);
    }
}
