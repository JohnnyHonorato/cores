package br.edu.ufcg.virtus.common.repository;

import br.edu.ufcg.virtus.common.domain.Comparison;

/**
 * Condition for item searches.
 *
 * @author Virtus
 *
 */
public class Condition {

    /**
     * Field.
     */
    private final String field;

    /**
     * Type of comparison.
     */
    private final Comparison comparison;

    /**
     * Value.
     */
    private Object value;

    /**
     * Constructor.
     *
     * @param field
     *            Field.
     * @param comparison
     *            Comparison.
     * @param value
     *            Value.
     */
    Condition(String field, Comparison comparison, Object value) {
        this.field = field;
        this.comparison = comparison;
        this.value = value;
    }

    /**
     * Gets the field.
     *
     * @return Field.
     */
    public String getField() {
        return this.field;
    }

    /**
     * Gets the comparison.
     *
     * @return Comparison.
     */
    public Comparison getComparison() {
        return this.comparison;
    }

    /**
     * Gets the value as String.
     *
     * @return Value.
     */
    public String getTextValue() {
        return String.valueOf(this.value).trim();
    }

    /**
     * Gets the value.
     *
     * @return Value.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value
     *            Value.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Condition with filter.
     *
     * @author Virtus
     */
    public interface ConditionWithField {

        /**
         * Sets the comparison.
         * 
         * @param comparison
         *            Comparison.
         * @return Condition comparing something.
         */
        ConditionComparing is(Comparison comparison);
    }

    /**
     * Condition comparing something.
     *
     * @author Virtus
     */
    public interface ConditionComparing {

        /**
         * Sets the value to compare.
         * 
         * @param value
         *            Value.
         * @return Condition completed.
         */
        ConditionComplete value(Object value);
    }

    /**
     * Condition completed.
     *
     * @author Virtus
     */
    public interface ConditionComplete {

        /**
         * Builds the condition.
         * 
         * @return Condition.
         */
        Condition build();
    }

    /**
     * Builder for: Condition.
     *
     * @author Virtus
     */
    public static class ConditionBuilder implements ConditionWithField, ConditionComparing, ConditionComplete {

        /**
         * Field.
         */
        private String field;

        /**
         * Comparison.
         */
        private Comparison comparison;

        /**
         * Value.
         */
        private Object value;

        /**
         * Sets the field.
         *
         * @param field
         *            Field.
         * @return Condition with field.
         */
        public ConditionWithField field(String field) {
            this.field = field;
            return this;
        }

        /**
         * (non-Javadoc)
         * 
         * @see br.edu.ufcg.virtus.core.repository.Condition.ConditionWithField#is(br.edu.ufcg.virtus.core.domain.Comparison)
         */
        @Override
        public ConditionComparing is(Comparison comparison) {
            this.comparison = comparison;
            return this;
        }

        /**
         * (non-Javadoc)
         * 
         * @see br.edu.ufcg.virtus.core.repository.Condition.ConditionComparing#value(java.lang.Object)
         */
        @Override
        public ConditionComplete value(Object value) {
            this.value = value;
            return this;
        }

        /**
         * (non-Javadoc)
         * 
         * @see br.edu.ufcg.virtus.core.repository.Condition.ConditionComplete#build()
         */
        @Override
        public Condition build() {
            return new Condition(this.field, this.comparison, this.value);
        }
    }
}
