package br.edu.ufcg.virtus.common.config;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.ufcg.virtus.common.exception.BusinessException;
import br.edu.ufcg.virtus.common.exception.RestException;
import br.edu.ufcg.virtus.common.util.MessageUtil;

/**
 * Handler for REST Exceptions.
 *
 * @author Potter
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final String UNEXPECTED_ERROR = "exception.unexpected";
    private static final String NULL_POINTER_ERROR = "exception.nullpointer";
    private static final String ILLEGAL_ARGUMENT_ERROR = "exception.illegalargument";
    private static final String ACCESS_DENIED_ERROR = "exception.accessdenied";
    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());

    /**
     * Handler for: Business Exception.
     *
     * @param ex
     *            Exception.
     * @param locale
     *            Locale.
     * @return Message response.
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<RestMessageDTO> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new RestMessageDTO(ex.getMessage(), ex.getCode()), ex.getStatus());
    }

    /**
     * Handler for: Illegal Argument.
     *
     * @param ex
     *            Exception.
     * @param locale
     *            Locale.
     * @return Message response.
     */
    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<RestMessageDTO> handleIllegalArgument(RestException ex) {
        final String errorMessage = MessageUtil.findMessage(ex.getMessage(), LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new RestMessageDTO(errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler for: Argument Not Valid.
     *
     * @param ex
     *            Exception.
     * @param locale
     *            Locale.
     * @return Message response.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestMessageDTO> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        final BindingResult result = ex.getBindingResult();

        final List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(objectError -> MessageUtil.findMessage(objectError.getCode(), LocaleContextHolder.getLocale()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new RestMessageDTO(errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<RestMessageDTO> nullPointerException(NullPointerException exception) {
        final String message = MessageUtil.findMessage(NULL_POINTER_ERROR, LocaleContextHolder.getLocale());
        LOGGER.log(Level.ALL, exception.toString(), exception);
        return new ResponseEntity<>(new RestMessageDTO(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<RestMessageDTO> illegalArgumentException(IllegalArgumentException exception) {
        final String message = MessageUtil.findMessage(ILLEGAL_ARGUMENT_ERROR, LocaleContextHolder.getLocale());
        LOGGER.log(Level.ALL, exception.toString(), exception);
        return new ResponseEntity<>(new RestMessageDTO(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<RestMessageDTO> accessDeniedException(AccessDeniedException exception) {
        final String message = MessageUtil.findMessage(ACCESS_DENIED_ERROR, LocaleContextHolder.getLocale());
        LOGGER.log(Level.ALL, exception.toString(), exception);
        return new ResponseEntity<>(new RestMessageDTO(message), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<RestMessageDTO> authenticationException(AuthenticationException exception) {
        final String message = MessageUtil.findMessage(ACCESS_DENIED_ERROR, LocaleContextHolder.getLocale());
        LOGGER.log(Level.ALL, exception.toString(), exception);
        return new ResponseEntity<>(new RestMessageDTO(message), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<RestMessageDTO> constraintViolationException(ConstraintViolationException exception) {
        final String message = exception.getConstraintViolations().stream().findFirst().get().getMessage();
        LOGGER.log(Level.ALL, exception.toString(), exception);
        return new ResponseEntity<>(new RestMessageDTO(message), HttpStatus.FORBIDDEN);
    }

    /**
     * Handler for: All Exceptions.
     *
     * @param ex
     *            Exception.
     * @param locale
     *            Locale.
     * @return Message response.
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestMessageDTO> handleExceptions(Exception ex) {
        final String errorMessage = MessageUtil.findMessage(UNEXPECTED_ERROR, LocaleContextHolder.getLocale());
        LOGGER.log(Level.ALL, ex.toString(), ex);
        return new ResponseEntity<>(new RestMessageDTO(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}