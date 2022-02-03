package br.edu.ufcg.virtus.common.util;

import org.apache.commons.lang3.StringUtils;

import br.edu.ufcg.virtus.common.exception.BusinessException;

/**
 * Utility class for validations.
 *
 * @author Virtus
 */
public final class ValidationUtil {

    /**
     * Private constructor.
     */
    private ValidationUtil() {
    }

    /**
     * Validate if the attributes are valid.
     *
     * @param name Name to be validated.
     * @param email Email to be validated.
     * @param cpf Cpf to be validated.
     *
     * @throws BusinessException
     */
    public static void validateAttributes(String name, String email, String cpf) throws BusinessException {
        if (isFieldBlank(name)) {
            throw new BusinessException("name.field.blank");
        } else if (isFieldBlank(email)) {
            throw new BusinessException("email.field.blank");
        } else if (!isFieldBlank(cpf)) {
            validateCpf(cpf);
        }

        if (isFieldBlank(email)) {
            throw new BusinessException("email.field.malformed");
        } else {
            validateEmail(email);
        }
    }

    /**
     * Checks if the field is blank.
     *
     * @param field The field to be checked.
     * @throws BusinessException
     */
    public static boolean isFieldBlank(String field) {
        return StringUtils.trimToNull(field) == null;
    }

    /**
     * Validate if the cpf attribute is valid.
     *
     * @param cpf The cpf to be validated.
     * @throws BusinessException
     */
    public static void validateCpf(String cpf) throws BusinessException {
        final String pattern = "^\\d+(\\.\\d+)*$";
        if (!cpf.matches(pattern) ) {
            throw new BusinessException("cpf.field.invalid");
        }
    }

    /**
     * Validate if the email attribute is valid.
     *
     * @param email The email to be validated.
     * @throws BusinessException
     */
    public static void validateEmail(String email) throws BusinessException {
        final String pattern = "^(.+)@(.+)$";
        if (!email.matches(pattern) ) {
            throw new BusinessException("email.field.invalid");
        }
    }

    /**
     * Validate if the path attribute is valid.
     *
     * @param openMode The module open mode.
     * @param path The path to be validated.
     * @throws BusinessException
     */
    public static void validatePathField(String openMode, String path) throws BusinessException {
        if (openMode.equals("SPA") && StringUtils.trimToNull(path) == null) {
            throw new BusinessException("path.field.blank");
        }
    }
}
