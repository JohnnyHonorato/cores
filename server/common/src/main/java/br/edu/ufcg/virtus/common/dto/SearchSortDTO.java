package br.edu.ufcg.virtus.common.dto;

/**
 * Sorting information DTO
 */
public class SearchSortDTO {
    /**
     * Sort order
     */
    private String order = "";

    /**
     * Column to be sorted
     */
    private String column = "";

    /**
     * Gets the column to be sorted
     * @return {@link String}
     */
    public String getColumn() {
        return column;
    }

    /**
     * Gets the sorting order
     * @return {@link String}
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets the column to be sorted
     * @param column {@link String}
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * Sets the sorting order
     * @param order {@link String}
     */
    public void setOrder(String order) {
        this.order = order;
    }
}
