package br.com.notification.api.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import br.com.notification.api.model.NotificationRequest;
import br.com.notification.api.model.NotificationResponse;
import br.com.notification.api.service.INotificationService;
import br.com.notification.api.utils.Constants;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class NotificationServiceImpl implements INotificationService {

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;

	@Override
	public NotificationResponse sendEmail(NotificationRequest notification) {
		MimeMessage message = sender.createMimeMessage();
		NotificationResponse response = new NotificationResponse();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
						
			String html = buildEmailData(notification);

			helper.setTo(notification.getRecipient());
			helper.setText(html, true);
			helper.setSubject(Constants.ORDER_CONFIRMED_SUBJECT);
			helper.setFrom("noreply@amazoniarc.com.br");
			sender.send(message);
						
			response.setMessage("Email Sending successfully");			

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Email Sending failure");		
		}
		
		return response;
	}

	private String buildEmailData(NotificationRequest notification) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, Object> model = new HashMap<>();
		model.put("email", notification.getRecipient());						
		Template template = config.getTemplate("template-email.ftl");
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		
	}

}
