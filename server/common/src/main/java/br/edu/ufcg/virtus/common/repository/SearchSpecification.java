package br.edu.ufcg.virtus.common.repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.jpa.domain.Specification;

import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.common.model.Searchable;

/**
 * Search specification for filtering the items.
 *
 * @param <M>
 *            Model type.
 * @param <T>
 *            ID type.
 * @author Virtus
 */
public class SearchSpecification<M extends Model<T>, T extends Serializable> implements Specification<M> {

    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map to cache Models that were already searched using the simple search mechanism
     */
    private static final Map<Class<?>, List<Field>> cachedFields = new HashMap<>();

    /**
     * Conditions.
     */
    private final List<Condition> conditions;

    /**
     * Input to search.
     */
    private final String input;

    /**
     * Constructor.
     *
     * @param conditions
     *            Conditions.
     * @param input
     *            Input.
     */
    public SearchSpecification(List<Condition> conditions, String input) {
        super();

        this.conditions = conditions;
        this.input = input;
    }

    /**
     * (non-Javadoc)
     *
     * @see Specification#toPredicate(Root, CriteriaQuery, CriteriaBuilder)
     */
    @Override
    public Predicate toPredicate(Root<M> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
        final List<Predicate> predicates = this.buildPredicates(root, builder);

        if (this.input != null) {
            predicates.add(this.buildSimpleSearchPredicate(root, builder));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    /**
     * Builds predicates to be used as the simple search function
     * 
     * @param root
     *            Root
     * @param criteriaBuilder
     *            CriteriaBuilder
     * @return OR predicate with all predicates generated for the class
     */
    private Predicate buildSimpleSearchPredicate(Root<M> root, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        final List<Field> fieldsToBeSearched = this.getSearchableFieldList(root);

        // Generate a Like Predicate for all type of fields, casting then as String
        fieldsToBeSearched.forEach(attr -> predicates.add(criteriaBuilder
                .like(criteriaBuilder.lower(root.get(attr.getName()).as(String.class)),
                        PredicateFactory.likeValue(this.input.toLowerCase()))));
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    /**
     * Get all fields annotated with {@link Searchable}, if empty we then use all String fields
     * 
     * @param root
     *            Root
     * @return List of Field that are either annotated with {@link Searchable} or are of type String if none are
     *         annotated
     */
    private List<Field> getSearchableFieldList(Root<M> root) {
        List<Field> fieldsToBeSearched = cachedFields.get(root.getJavaType());
        if (fieldsToBeSearched == null) {
            fieldsToBeSearched = FieldUtils.getFieldsListWithAnnotation(root.getJavaType(), Searchable.class);
            if (fieldsToBeSearched.isEmpty()) {
                fieldsToBeSearched = Arrays.stream(root.getJavaType().getDeclaredFields())
                        .filter(attr -> attr.getType().getSimpleName().equalsIgnoreCase("string"))
                        .collect(Collectors.toList());
            }
            cachedFields.put(root.getJavaType(), fieldsToBeSearched);
        }
        return fieldsToBeSearched;
    }

    /**
     * Builds all predicates by the conditions specified.
     *
     * @param root
     *            Root model.
     * @param criteriaBuilder
     *            Criteria builder.
     * @return {@link List}
     */
    private List<Predicate> buildPredicates(Root<M> root, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        this.conditions.forEach(condition -> predicates.add(this.predicate(condition, root, criteriaBuilder)));
        return predicates;
    }

    /**
     * Creates the predicate from a condition.
     *
     * @param condition
     *            Condition
     * @param root
     *            Root Model
     * @param criteriaBuilder
     *            CriteriaBuilder
     * @return Predicate.
     */
    private Predicate predicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
        try {
            return PredicateFactory.getPredicate(condition, root, criteriaBuilder);
        } catch (final NullPointerException ex) {
            return this.equals(condition, root, criteriaBuilder);
        }
    }

    /**
     * Creates the EQUALS predicate.
     *
     * @param condition
     *            Condition.
     * @param root
     *            Root Model
     * @param criteriaBuilder
     *            CriteriaBuilder
     * @return Equals predicate.
     */
    private Predicate equals(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.getField()), condition.getValue());
    }
}
