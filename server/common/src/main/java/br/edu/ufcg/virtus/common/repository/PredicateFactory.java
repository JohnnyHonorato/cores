package br.edu.ufcg.virtus.common.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.ufcg.virtus.common.domain.Comparison;

/**
 * Predicate Builder
 * <li>{@link BetweenPredicate}</li>
 * <li>{@link EqualPredicate}</li>
 * <li>{@link LikePredicate}</li>
 * <li>{@link InPredicate}</li>
 * <li>{@link AsStringPredicate}</li>
 * <li>{@link RelationshipEqualPredicate}</li>
 * <li>{@link RelationshipLikePredicate}</li>
 * <li>{@link RelationshipBetweenPredicate}</li>
 * <li>{@link RelationshipAsStringPredicate}</li>
 */
public class PredicateFactory {

    /**
     * Delimiter for relationship and between searches
     */
    public static final String DELIMITER = "->";
    /**
     * Map of predicate generators
     */
    private static final Map<Comparison, ToPredicate> COMPARISON_MAP = new EnumMap<>(Comparison.class);

    /**
     * Gets the proper Predicate based on {@link Condition#getComparison()}
     * 
     * @param condition
     *            Condition
     * @param root
     *            Root
     * @param criteriaBuilder
     *            CriteriaBuilder
     * @param <M>
     *            Root Model's Class
     * @return Proper Predicate
     */
    public static <M> Predicate getPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
        if (COMPARISON_MAP.isEmpty()) {
            initiateMap();
        }
        final ToPredicate predicateGenerator = COMPARISON_MAP.get(condition.getComparison());
        return predicateGenerator.toPredicate(condition, root, criteriaBuilder);
    }

    /**
     * Initiate {@link PredicateFactory#COMPARISON_MAP}
     */
    private static void initiateMap() {
        COMPARISON_MAP.put(Comparison.BETWEEN, new BetweenPredicate());
        COMPARISON_MAP.put(Comparison.LIKE, new LikePredicate());
        COMPARISON_MAP.put(Comparison.EQ, new EqualPredicate());
        COMPARISON_MAP.put(Comparison.IN, new InPredicate());
        COMPARISON_MAP.put(Comparison.AS_STRING, new AsStringPredicate());
        COMPARISON_MAP.put(Comparison.RELATIONSHIP_EQ, new RelationshipEqualPredicate());
        COMPARISON_MAP.put(Comparison.RELATIONSHIP_LIKE, new RelationshipLikePredicate());
        COMPARISON_MAP.put(Comparison.RELATIONSHIP_BETWEEN, new RelationshipBetweenPredicate());
        COMPARISON_MAP.put(Comparison.RELATIONSHIP_AS_STRING, new RelationshipAsStringPredicate());
    }

    /**
     * Gets the SQL LIKE form of string for the value
     * 
     * @param value
     *            Value
     * @return Value's SQL LIKE form e.g. %%fulano%%
     */
    public static String likeValue(String value) {
        return String.format("%%%s%%", value);
    }

    /**
     * Get two dates from a received string
     * 
     * @param rawDates
     *            e.g. 20/07/20->24/07/20
     * @return Array with two Date objects from those dates
     */
    public static Date[] getDates(String rawDates) {
        final String[] dates = rawDates.split(DELIMITER);
        try {
            final Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dates[0]);
            final Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dates[1]);
            return new Date[] { date1, date2 };
        } catch (final ParseException e) {
            throw new RuntimeException("Parsing error", e);
        }
    }

    /**
     * Interface to be implemented by all predicate generators
     */
    public interface ToPredicate {
        /**
         * Method that generates the proper predicate
         * 
         * @param condition
         *            Condition
         * @param root
         *            Root
         * @param criteriaBuilder
         *            CriteriaBuilder
         * @param <M>
         *            Model Class
         * @return Proper predicate
         */
        <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder);
    }

    /**
     * Between Predicate Generator
     */
    public static class BetweenPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            final Date[] dates = getDates(condition.getTextValue());
            return criteriaBuilder.between(root.get(condition.getField()), dates[0], dates[1]);
        }
    }

    /**
     * Like Predicate Generator
     */
    public static class LikePredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(condition.getField())),
                    PredicateFactory.likeValue(condition.getTextValue().toLowerCase()));
        }
    }

    /**
     * Equal Predicate Generator
     */
    public static class EqualPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(root.get(condition.getField()), condition.getValue());
        }
    }

    /**
     * In Predicate Generator
     */
    public static class InPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            final List<Predicate> predicates = new ArrayList<>();
            for (final String value : condition.getTextValue().split(",")) {
                condition.setValue(String.format(",%s,", value));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(condition.getField())),
                        likeValue(condition.getTextValue().toLowerCase())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    /**
     * Relationship Equal Predicate Generator
     */
    public static class RelationshipEqualPredicate extends RelationshipPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(this.getRelationshipPath(condition, root), condition.getValue());
        }
    }

    /**
     * Relationship Like Predicate Generator
     */
    public static class RelationshipLikePredicate extends RelationshipPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.like(criteriaBuilder.lower(this.getRelationshipPath(condition, root)),
                    likeValue(condition.getTextValue().toLowerCase()));
        }
    }

    /**
     * Relationship Between Predicate generator
     */
    public static class RelationshipBetweenPredicate extends RelationshipPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            final Date[] dates = getDates(condition.getTextValue());
            return criteriaBuilder.between(this.getRelationshipPath(condition, root), dates[0], dates[1]);
        }
    }

    /**
     * Relationship Convert Field As String Predicate Generator
     */
    public static class RelationshipAsStringPredicate extends RelationshipPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder
                    .like(criteriaBuilder.lower(this.getRelationshipPath(condition, root).as(String.class)),
                            likeValue(condition.getTextValue().toLowerCase()));
        }
    }

    /**
     * Casting field as string predicate
     */
    public static class AsStringPredicate implements ToPredicate {

        /**
         * (non-javadoc)
         * 
         * @see ToPredicate#toPredicate(Condition, Root, CriteriaBuilder)
         */
        @Override
        public <M> Predicate toPredicate(Condition condition, Root<M> root, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder
                    .like(criteriaBuilder.lower(root.get(condition.getField()).as(String.class)),
                            PredicateFactory.likeValue(condition.getTextValue().toLowerCase()));
        }
    }

    /**
     * Abstract class for the Relationship Predicate Generators
     */
    private abstract static class RelationshipPredicate {
        /**
         * Gets the attribute Path for relationship search
         * 
         * @param condition
         *            Condition
         * @param root
         *            Root
         * @param <T>
         *            Type of the Path atribute
         * @return Path
         */
        protected <M, T> Path<T> getRelationshipPath(Condition condition, Root<M> root) {
            final String[] properties = condition.getField().split(DELIMITER);

            Path<T> path = root.get(properties[0]);
            for (int i = 1; i < properties.length; i++) {
                path = path.get(properties[i]);
            }
            return path;
        }
    }
}
