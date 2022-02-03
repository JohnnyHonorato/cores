package br.edu.ufcg.virtus.core.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.edu.ufcg.virtus.core.util.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {

    private static final String TEMPLATE_PATH = "/templates/email/";

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Async
    public void sendSimpleMessage(Mail mail, String templateName) throws MessagingException, IOException, TemplateException {
        final MimeMessage message = this.emailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        this.freemarkerConfig.setClassForTemplateLoading(this.getClass(), EmailService.TEMPLATE_PATH);
        final Template t = this.freemarkerConfig.getTemplate(templateName);
        final String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());
        helper.addInline("logo.png", new ClassPathResource("images/logo.png"));

        if (mail.getAttachments() != null && mail.getAttachments().size() > 0) {
            for (final File file : mail.getAttachments()) {
                helper.addAttachment(file.getName(), file);
            }
        }
        this.emailSender.send(message);
    }

}