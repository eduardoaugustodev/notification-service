package br.com.notification.api.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.notification.api.model.NotificationRequest;
import br.com.notification.api.model.NotificationResponse;
import br.com.notification.api.service.INotificationService;

@RestController
@RequestMapping("/notificar")
public class NotificationServiceResource {

	@Autowired
	private INotificationService service;

	@PostMapping
	public  ResponseEntity<NotificationResponse> sendEmail(@Valid @RequestBody NotificationRequest notification) {
		return ResponseEntity.ok(this.service.sendEmail(notification));
	}
}
