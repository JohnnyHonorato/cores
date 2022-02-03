package br.edu.ufcg.virtus.common.domain;

/**
 * Domain for Comparison operations.
 * 
 * @author Virtus
 *
 */
public enum Comparison {
    /**
     * Equals
     */
    EQ,

    /**
     * Multiple values in a WHERE clause
     */
    IN,

    /**
     * Contains
     */
    LIKE,

    /**
     * Between two dates
     */
    BETWEEN,

    /**
     * Relationship Equal comparison
     */
    RELATIONSHIP_EQ,

    /**
     * Relationship Like comparison
     */
    RELATIONSHIP_LIKE,

    /**
     * Relationship Between comparison
     */
    RELATIONSHIP_BETWEEN,

    /**
     * Relationship comparison converting field as string
     */
    RELATIONSHIP_AS_STRING,

    /**
     * Cast Field As String
     */
    AS_STRING
}
