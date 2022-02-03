package br.edu.ufcg.virtus.tracker.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.virtus.common.repository.CrudBaseRepository;
import br.edu.ufcg.virtus.tracker.enums.AttributeType;
import br.edu.ufcg.virtus.tracker.model.AttributeValue;

public interface AttributeValueRepository extends CrudBaseRepository<AttributeValue, Integer> {

    @Modifying
    @Transactional
    void deleteByAttributeId(Integer attributeId);

    List<AttributeValue> findAllByAttribute_TypeAndValueAndAttribute_TrackerModel_DeletedIsFalse(AttributeType parentAttributeType, String parentAttributeValue);

    List<AttributeValue> findAllByTracker_IdAndAttribute_RelatedAttribute_Id(Integer trackerId, Integer relatedAttributeId);
}
