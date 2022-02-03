package br.edu.ufcg.virtus.common.config;

import java.util.List;

import br.edu.ufcg.virtus.common.dto.ModelDTO;

/**
 * DTO for REST messages.
 *
 * @author Virtus
 *
 */
public class RestMessageDTO extends ModelDTO {

    /**
     * Code.
     */
    private String code;

    /**
     * Message.
     */
    private String message;

    /**
     * List of messages.
     */
    private List<String> messages;

    /**
     * Constructor.
     *
     * @param messages
     *            Messages.
     */
    public RestMessageDTO(List<String> messages) {
        if (!messages.isEmpty()) {
            this.message = messages.get(0);
        }
        this.messages = messages;
    }

    /**
     * Constructor.
     *
     * @param message
     *            Message.
     */
    public RestMessageDTO(String message) {
        this.message = message;
    }

    /**
     * Constructor.
     *
     * @param message
     *            Message.
     * @param code
     *            Code.
     */
    public RestMessageDTO(String message, String code) {
        this.message = message;
        this.code = code;
    }

    /**
     * Gets the code.
     *
     * @return Code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Sets the code.
     * 
     * @param code
     *            Code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the message.
     *
     * @return Message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message.
     * 
     * @param message
     *            Message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the list of messages.
     * 
     * @return List of messages.
     */
    public List<String> getMessages() {
        return this.messages;
    }

    /**
     * Sets the list of messages.
     * 
     * @param messages
     *            List of messages.
     */
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}