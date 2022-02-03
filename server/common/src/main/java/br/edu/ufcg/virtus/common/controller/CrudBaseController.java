package br.edu.ufcg.virtus.common.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.ufcg.virtus.common.dto.ModelDTO;
import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.model.Model;
import br.edu.ufcg.virtus.common.security.authorization.DeletePermission;
import br.edu.ufcg.virtus.common.security.authorization.InsertPermission;
import br.edu.ufcg.virtus.common.security.authorization.UpdatePermission;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.common.util.NullAwareBeanUtils;
import io.swagger.annotations.ApiOperation;

/**
 * The 'CrudBaseController' class provides the common API for CRUD controllers.
 * <p>
 * If a controller is a model CRUD, this controller MUST extend this class.
 * <p>
 * Provides insertion, update and deletion for the model.
 *
 * @param <M>
 *            Model type.
 * @param <T>
 *            Model ID type.
 * @param <D>
 *            Model DTO type.
 * @author Virtus
 */
public abstract class CrudBaseController<M extends Model<T>, T extends Serializable, D extends ModelDTO>
extends SearchBaseController<M, T, D> {

    /**
     * Gets the model CRUD service.
     *
     * @return Model CRUD service.
     */
    @Override
    protected abstract CrudService<M, T> getService();

    /**
     * Inserts the model instance.
     *
     * @param modelDTO
     *            Model DTO.
     * @return Response.
     * @throws BusinessException
     */
    @ApiOperation(value = "Add an item")
    @PostMapping
    @InsertPermission
    public ResponseEntity<D> insert(HttpServletRequest request, @Valid @RequestBody D modelDTO) throws BusinessException {
        final M model = this.getService().insert(this.toModel(modelDTO));
        return this.created(this.toDTO(model));
    }

    /**
     * Updates the model instance.
     *
     * @param id
     *            ID of instance.
     * @param modelDTO
     *            Model DTO.
     * @return Response.
     * @throws BusinessException
     */
    @ApiOperation(value = "Update an item")
    @PutMapping("/{id}")
    @UpdatePermission
    public ResponseEntity<D> update(HttpServletRequest request, @Valid @PathVariable T id, @RequestBody D modelDTO)
            throws BusinessException {
        this.getService().update(id, this.toModel(modelDTO));
        return this.ok(modelDTO);
    }

    /**
     * Updates the model instance partially.
     *
     * @param id
     *            ID of instance.
     * @param modelDTO
     *            Model.
     * @return Response.
     * @throws Exception
     */
    @ApiOperation(value = "Update partial of an item")
    @PatchMapping("/{id}")
    @UpdatePermission
    public ResponseEntity<D> updatePartial(HttpServletRequest request, @PathVariable T id, @RequestBody D modelDTO) throws Exception {
        final M model = this.toModel(modelDTO);
        final M dbModel = this.getService().getOne(id);
        NullAwareBeanUtils.getInstance().copyProperties(dbModel, model);
        this.getService().update(id, dbModel);
        return this.ok(modelDTO);
    }

    /**
     * Deletes the model instance.
     *
     * @param id
     *            ID of instance.
     * @return Response.
     * @throws Exception
     */
    @ApiOperation(value = "Delete an item")
    @DeleteMapping("/{id}")
    @DeletePermission
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable T id) throws BusinessException {
        this.getService().delete(id);

        return this.success();
    }

    /**
     * Response CREATED (201) for the REST requests.
     *
     * @param element
     *            Element.
     * @return CREATED (201).
     */
    protected <E> ResponseEntity<E> created(E element) {
        return new ResponseEntity<>(element, HttpStatus.CREATED);
    }
}
