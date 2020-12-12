package br.com.notification.api.service;

import br.com.notification.api.model.NotificationRequest;
import br.com.notification.api.model.NotificationResponse;

public interface INotificationService {

	NotificationResponse sendEmail(NotificationRequest notification);
}
