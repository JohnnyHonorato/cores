package br.edu.ufcg.virtus.common.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import br.edu.ufcg.virtus.common.util.MessageUtil;

/**
 * Exception for business errors or validations. <br>
 *
 * Should contain a user friendly message. <br>
 * Ex: "There is an item with the same name".
 *
 * @author Virtus
 */
public class BusinessException extends Exception {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Exception code.
     */
    private String code;

    /**
     * status HttpStatus
     */
    private HttpStatus status = HttpStatus.CONFLICT;

    /**
     * Constructor.
     *
     * @param code
     *            Message Code.
     */
    public BusinessException(String code) {
        this(code, new Locale("pt", "BR"));
    }

    /**
     * Constructor.
     *
     * @param code
     *            Code.
     * @param locale
     *            Locale.
     */
    public BusinessException(String code, Locale locale) {
        this(code, locale, null);
    }

    /**
     * Constructor.
     *
     * @param code
     *            Message code.
     * @param e
     *            Exception.
     */
    public BusinessException(String code, Locale locale, Exception e) {
        super(MessageUtil.findMessage(code, locale), e);
        this.code = code;

        this.logger.error(this.getMessage());
    }

    public BusinessException(String code, HttpStatus status) {
        super(MessageUtil.findMessage(code, new Locale("pt", "BR")));
        this.status = status;
        this.code = code;
        this.logger.error(this.getMessage());
        this.fillInStackTrace();
    }
    
    public BusinessException(String code, String value, HttpStatus status) {
        super(String.format(MessageUtil.findMessage(code, new Locale("pt", "BR")), value));
        this.status = status;
        this.code = code;
        this.logger.error(this.getMessage());
        this.fillInStackTrace();
    }

    /**
     * Gets the exception code.
     *
     * @return Exception code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the exception code.
     *
     * @param code
     *            Exception code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
