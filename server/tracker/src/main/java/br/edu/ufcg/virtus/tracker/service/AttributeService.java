package br.edu.ufcg.virtus.tracker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.service.CrudService;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import br.edu.ufcg.virtus.tracker.model.Attribute;
import br.edu.ufcg.virtus.tracker.model.TrackerModel;
import br.edu.ufcg.virtus.tracker.repository.AttributeRepository;

@Service
public class AttributeService extends CrudService<Attribute, Integer> {

    @Autowired
    private AttributeRepository repository;

    @Autowired
    private AttributeValueService attributeValueService;

    private final int STRING_MIN_LENGTH = 1;
    private final int STRING_MAX_LENGTH = 255;

    @Override
    protected AttributeRepository getRepository() {
        return this.repository;
    }

    public void insertMultipleAttributesFromTrackerModel(TrackerModel model) throws BusinessException {

        if (model.getAttributes() != null && !model.getAttributes().isEmpty()) {

            this.collectRelatedAttributeEntities(model);

            final List<Attribute> attributesWithoutRelationship = model.getAttributes().stream()
                    .filter(attribute -> attribute.getRelatedAttribute() == null).collect(Collectors.toList());
            final List<Attribute> persistedAttributes = new ArrayList<>();
            for (final Attribute attribute : attributesWithoutRelationship) {
                this.saveAttribute(model, attribute, this.getAttributeRelationships(model.getAttributes()), persistedAttributes, model.getAttributes());
            }
        }
    }

    private void collectRelatedAttributeEntities(TrackerModel model) {
        model.getAttributes().stream().forEach(attribute -> {
            if (attribute.getRelatedAttribute() != null) {
                final Attribute attributeEntity = model.getAttributes().stream().filter(attributeSearch -> attributeSearch.getTitle().equals(attribute.getRelatedAttribute().getTitle())).findFirst().orElse(null);
                attribute.setRelatedAttribute(attributeEntity);
            }
        });
    }

    private void saveAttribute(TrackerModel model, Attribute attribute, Map<Attribute, List<Attribute>> attributeRelationships, List<Attribute> persistedAttributes, List<Attribute> allAttributes) throws BusinessException {
        if (!persistedAttributes.contains(attribute)) {
            if (!attribute.isDeleted()) {
                this.validateRequiredFields(attribute, model.getId(), allAttributes);
                this.validateAttributeType(attribute);
            }
            attribute.setTrackerModel(model);
            this.repository.save(attribute);
            persistedAttributes.add(attribute);

            for (final Attribute attributeRelationship : attributeRelationships.get(attribute)) {
                this.saveAttribute(model, attributeRelationship, attributeRelationships, persistedAttributes, allAttributes);
            }
        }
    }

    private Map<Attribute, List<Attribute>> getAttributeRelationships(List<Attribute> attributes) {

        final Map<Attribute, List<Attribute>> attributeRelationships = new HashMap<>();

        attributes.forEach(attribute -> {

            final List<Attribute> childAttributes = attributes.stream()
                    .filter(childAttribute -> childAttribute.getRelatedAttribute() != null ?
                            childAttribute.getRelatedAttribute().getTitle().equals(attribute.getTitle())
                            : false)
                    .collect(Collectors.toList());

            attributeRelationships.put(attribute, childAttributes);
        });
        return attributeRelationships;
    }

    @Override
    public void delete(Integer id) throws BusinessException {
        if (!this.repository.existsById(id)) {
            throw new BusinessException("tracker-model.attribute.not.found", HttpStatus.BAD_REQUEST);
        }
        this.attributeValueService.deleteByAttributeId(id);
        this.repository.deleteById(id);
    }


    public String getCurrencyByAttributeNameAndTrackerModel(Integer trackerModelId, String attributeName) {
        return this.repository.getCurrencyByAttributeNameAndTrackerModel(trackerModelId, attributeName);
    }

    private void validateRequiredFields(Attribute attribute, Integer trackerModelId, List<Attribute> allAttributes) throws BusinessException {
        if (attribute.getType() == null) {
            throw new BusinessException("tracker-model.attribute.type.empty", HttpStatus.BAD_REQUEST);
        }
        if (Strings.isBlank(attribute.getTitle())) {
            throw new BusinessException("tracker-model.attribute.name.empty", HttpStatus.BAD_REQUEST);
        }

        boolean exists = false;

        Attribute attributeWithName = this.repository.getByTitleAndTrackerModelIdAndDeletedFalse(attribute.getTitle(), trackerModelId);

        if (attributeWithName != null) {
            exists = allAttributes.stream().anyMatch(att -> att.getId() != attribute.getId()
                    && att.getId() == attributeWithName.getId()
                    && att.getTitle() == attribute.getTitle());
        }

        if (exists) {
            throw new BusinessException("tracker-model.attribute.name.exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateAttributeType(Attribute attribute) throws BusinessException {
        switch (attribute.getType()) {
            case INTEGER:
            case DECIMAL:
                this.validateNumberType(attribute);
                break;
            case STRING:
                this.validateStringType(attribute);
                break;
            case LIST:
                this.validateListType(attribute);
                break;
            case DATE:
                this.validateDateType(attribute);
                break;
            default:
                break;
        }
    }

    private void validateNumberType(Attribute attribute) throws BusinessException {
        if (attribute.getMinValue() != null && attribute.getMaxValue() != null &&
                attribute.getMinValue() > attribute.getMaxValue()) {
            if (AttributeType.INTEGER.equals(attribute.getType())) {
                throw new BusinessException("tracker-model.attribute.type.integer.min-greater-than-max", HttpStatus.BAD_REQUEST);
            } else {
                throw new BusinessException("tracker-model.attribute.type.decimal.min-greater-than-max", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void validateStringType(Attribute attribute) throws BusinessException {
        if (attribute.getMaxLength() < this.STRING_MIN_LENGTH) {
            throw new BusinessException("tracker-model.attribute.type.string.min-length", HttpStatus.BAD_REQUEST);
        }
        if (attribute.getMaxLength() > this.STRING_MAX_LENGTH) {
            throw new BusinessException("tracker-model.attribute.type.string.max-length", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateListType(Attribute attribute) throws BusinessException {
        if (Strings.isBlank(attribute.getListValues())) {
            throw new BusinessException("tracker-model.attribute.type.list.options-empty", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDateType(Attribute attribute) throws BusinessException {
        if (attribute.getMinDate() != null && attribute.getMaxDate() != null &&
                attribute.getMinDate().getTimeInMillis() > attribute.getMaxDate().getTimeInMillis()) {
            throw new BusinessException("tracker-model.attribute.type.date.min-greater-than-max", HttpStatus.BAD_REQUEST);
        }
    }

}
