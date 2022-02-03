package br.edu.ufcg.virtus.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for filter the search.
 *
 * @author Virtus
 */
public class SearchFilterDTO {

    /**
     * Current page.
     */
    private Integer currentPage = 1;

    /**
     * Page size.
     */
    private Integer pageSize = 10;

    /**
     * Sort information (Column to be sorted, order)
     * {@link br.edu.ufcg.virtus.core.dto.SearchSortDTO}
     */
    private SearchSortDTO sort;

    /**
     * Value to search in all columns.
     */
    private String search = "";

    /**
     * Filters.
     */
    private List<FilterDTO> filters = new ArrayList<>();

    /**
     * Gets the current page.
     *
     * @return Current page.
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page.
     *
     * @param currentPage Current page.
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets the searchSortDTO
     * @return {@link br.edu.ufcg.virtus.core.dto.SearchSortDTO}
     */
    public SearchSortDTO getSort() {
        return sort;
    }

    /**
     * Sets the searchSortDTO
     * @param sort {@link br.edu.ufcg.virtus.core.dto.SearchSortDTO}
     */
    public void setSort(SearchSortDTO sort) {
        this.sort = sort;
    }

    /**
     * Gets the page size.
     *
     * @return Page size.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize Page size.
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Get the filters.
     *
     * @return Filters.
     */
    public List<FilterDTO> getFilters() {
        return filters;
    }

    /**
     * Sets the filters.
     *
     * @param filters Filters.
     */
    public void setFilters(List<FilterDTO> filters) {
        this.filters = filters;
    }

    /**
     * Get the search value.
     *
     * @return
     */
    public String getSearch() {
        return search;
    }

    /**
     * Set the search value.
     *
     * @param search
     */
    public void setSearch(String search) {
        this.search = search;
    }
}
