package br.edu.ufcg.virtus.tracker.service.validation;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.tracker.model.AttributeValue;

public class AttributeValueValidationHelper {

    public static void Validate(AttributeValue model) throws BusinessException{
        validateAttributeValue(model);
        if (Strings.isNotBlank(model.getValue()) || Strings.isNotBlank(model.getValueComplement())) {
            validateAttributeType(model);
        }
    }

    private static void validateAttributeValue(AttributeValue model) throws BusinessException {
        if (model.getAttribute().getRequired() && Strings.isBlank(model.getValue())) {
            throw new BusinessException("tracker.attribute-value.validation.required", HttpStatus.BAD_REQUEST);
        }
        if (model.getTracker().getTransition() != null) {
            final String[] allowedTransitions = model.getTracker().getTransition().getAttributes().split(";");
            if (Arrays.stream(allowedTransitions).noneMatch(model.getAttribute().getTitle()::equals)) {
                throw new BusinessException("tracker.attribute-value.validation.transition-attribute.not-allowed", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private static void validateAttributeType(AttributeValue model) throws BusinessException {
        switch (model.getAttribute().getType()) {
            case INTEGER:
                validateInteger(model);
                break;
            case DECIMAL:
                validateDecimal(model);
                break;
            case LIST:
                validateList(model);
                break;
            case STRING:
                validateString(model);
                break;
            case DATE:
                validateDate(model);
                break;
            case DATE_TIME:
                validateDateTime(model);
                break;
            default:
                break;
        }
    }

    private static void validateInteger(AttributeValue model) throws BusinessException {
        try {
            final Integer value = Integer.parseInt(model.getValue());
            if (model.getAttribute().getMinValue() != null && value < model.getAttribute().getMinValue()) {
                throw new BusinessException("tracker.attribute-value.validation.integer.min", HttpStatus.BAD_REQUEST);
            }
            if (model.getAttribute().getMaxValue() != null && value > model.getAttribute().getMaxValue()) {
                throw new BusinessException("tracker.attribute-value.validation.integer.max", HttpStatus.BAD_REQUEST);
            }
        } catch (final NumberFormatException e) {
            throw new BusinessException("tracker.attribute-value.validation.integer.not-integer", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateDecimal(AttributeValue model) throws BusinessException {
        try {
            final Double value = Double.parseDouble(model.getValue());
            if (model.getAttribute().getMinValue() != null && value < model.getAttribute().getMinValue()) {
                throw new BusinessException("tracker.attribute-value.validation.decimal.min", HttpStatus.BAD_REQUEST);
            }
            if (model.getAttribute().getMaxValue() != null && value > model.getAttribute().getMaxValue()) {
                throw new BusinessException("tracker.attribute-value.validation.decimal.max", HttpStatus.BAD_REQUEST);
            }
        } catch (final NumberFormatException e) {
            throw new BusinessException("tracker.attribute-value.validation.decimal.not-decimal", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateList(AttributeValue model) throws BusinessException {
        final String[] allowedValues = model.getAttribute().getListValues().split(";");
        if (Arrays.stream(allowedValues).noneMatch(model.getValue()::equals)) {
            throw new BusinessException("tracker.attribute-value.validation.list.not-allowed", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateString(AttributeValue model) throws BusinessException {
        if (model.getValue().length() > model.getAttribute().getMaxLength()) {
            throw new BusinessException("tracker.attribute-value.validation.string.length", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateDate(AttributeValue model) throws BusinessException {
        try {
            final Date date = Date.from(Instant.parse(model.getValue()));

            if (model.getAttribute().getMinDate() != null) {
                final Date minDate = model.getAttribute().getMinDate().getTime();
                if (date.before(minDate)) {
                    throw new BusinessException("tracker.attribute-value.validation.date.min", HttpStatus.BAD_REQUEST);
                }
            }
            if (model.getAttribute().getMaxDate() != null) {
                final Date maxDate = model.getAttribute().getMaxDate().getTime();
                if (date.after(maxDate)) {
                    throw new BusinessException("tracker.attribute-value.validation.date.max", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (final DateTimeParseException e) {
            throw new BusinessException("tracker.attribute-value.validation.date.invalid", HttpStatus.BAD_REQUEST);
        }
    }

    private static void validateDateTime(AttributeValue model) throws BusinessException {
        try {
            Instant.parse(model.getValue());
        } catch (final DateTimeParseException e) {
            throw new BusinessException("tracker.attribute-value.validation.datetime.invalid", HttpStatus.BAD_REQUEST);
        }
    }

}
