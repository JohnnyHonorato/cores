package br.edu.ufcg.virtus.core.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

    private String from;
    private String to;
    private String subject;
    private List<File> attachments;
    private Map<String, Object> model;

    public Mail() {

    }

    @SuppressWarnings("unchecked")
    public void setModel(Object object) {
        this.model = (Map<String, Object>) object;
    }

}