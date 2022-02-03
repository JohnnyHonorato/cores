package br.edu.ufcg.virtus.common.service;

import java.io.Serializable;

import br.edu.ufcg.virtus.common.dto.PageListDTO;
import br.edu.ufcg.virtus.common.dto.SearchFilterDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.common.repository.SearchBaseRepository;

/**
 * The 'SearchService' provides the common API for all services that do search operations with models.
 *
 * All search model services MUST extend this class.
 *
 * @author Virtus
 *
 * @param <M>
 *            Model type.
 * @param <T>
 *            ID type.
 */
public abstract class SearchService<M extends Model<T>, T extends Serializable> extends BaseService {

    /**
     * Gets the search Repository.
     *
     * @return Search Repository.
     */
    protected abstract SearchBaseRepository<M, T> getRepository();

    /**
     * Searches the model with the filter.
     *
     * @param filter
     *            Filter.
     * @return Instances founded.
     */
    public PageListDTO search(SearchFilterDTO filter) {
        return this.getRepository().search(filter);
    }

    /**
     * Gets one model instance by ID.
     *
     * @param id
     *            ID.
     * @return Instance founded.
     * @throws BusinessException
     */
    public M getOne(T id) throws BusinessException {
        return this.getRepository().findById(id).get();
    }
}
