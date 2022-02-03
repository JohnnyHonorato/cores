package br.edu.ufcg.virtus.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import br.edu.ufcg.virtus.tracker.model.AttributeValue;
import br.edu.ufcg.virtus.tracker.model.Tracker;
import br.edu.ufcg.virtus.tracker.repository.AttributeValueRepository;
import br.edu.ufcg.virtus.tracker.service.validation.AttributeValueValidationHelper;

@Service
public class AttributeValueService extends CrudService<AttributeValue, Integer> {

    @Autowired
    private AttributeValueRepository repository;

    @Autowired
    private AttributeService attributeService;

    @Override
    protected AttributeValueRepository getRepository() {
        return this.repository;
    }

    @Override
    protected AttributeValue preInsert(AttributeValue model) throws BusinessException {
        model = this.fetchAttribute(model);
        return super.preInsert(model);
    }

    @Override
    protected AttributeValue preUpdate(AttributeValue model) throws BusinessException {
        model = this.fetchAttribute(model);
        return super.preUpdate(model);
    }

    private AttributeValue fetchAttribute(AttributeValue model) throws BusinessException {
        model.setAttribute(this.attributeService.getOne(model.getAttribute().getId()));
        return model;
    }

    @Override
    protected void validateInsert(AttributeValue model) throws BusinessException {
        super.validateInsert(model);
        AttributeValueValidationHelper.Validate(model);
    }

    @Override
    protected void validateUpdate(AttributeValue model) throws BusinessException {
        super.validateUpdate(model);
        AttributeValueValidationHelper.Validate(model);
    }

    public void deleteByAttributeId(Integer attributeId) throws BusinessException {
        this.repository.deleteByAttributeId(attributeId);
    }

    public void saveAttributeValues(Tracker tracker) throws BusinessException {
        if (tracker.getAttributesValue() != null && !tracker.getAttributesValue().isEmpty()) {
            for (final AttributeValue trackerAttributeValue : tracker.getAttributesValue()) {
                if (trackerAttributeValue != null) {
                    trackerAttributeValue.setTracker(tracker);
                    if (trackerAttributeValue.getId() == null) {
                        this.insert(trackerAttributeValue);
                    } else {
                        this.update(trackerAttributeValue.getId(), trackerAttributeValue);
                    }
                }
            }
        }
    }

    public List<AttributeValue> getByTypeAndValue(AttributeType parentAttributeType, String parentAttributeValue) {
        return this.getRepository().findAllByAttribute_TypeAndValueAndAttribute_TrackerModel_DeletedIsFalse(parentAttributeType, parentAttributeValue);
    }

    public List<AttributeValue> getRelatedChildren(AttributeValue parentValue) throws BusinessException {
        return this.getRepository().findAllByTracker_IdAndAttribute_RelatedAttribute_Id(parentValue.getTracker().getId(), parentValue.getAttribute().getId());
    }

}
