package br.com.notification.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.notification.api.model.NotificationRequest;
import br.com.notification.api.model.NotificationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationServiceResourceTest {

	@LocalServerPort
	int randomServerPort;

	private RestTemplate restTemplate;

	private String urlBase;

	@BeforeEach
	void setUp() {
		this.restTemplate = new RestTemplate();
		this.urlBase = "http://localhost:" + randomServerPort + "/notification-service/notificar";
	}

	@Test
	void deveEnviarEmailComSucesso() {
		NotificationRequest request = new NotificationRequest();
		request.setRecipient("eduardo.augustolp@hotmail.com");
		ResponseEntity<NotificationResponse> response = restTemplate.postForEntity(urlBase, request,
				NotificationResponse.class);
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	void deveValidarRecipientInvalido() {
		Exception exception = assertThrows(RuntimeException.class, () -> {
			NotificationRequest request = new NotificationRequest();
			restTemplate.postForEntity(urlBase, request, NotificationResponse.class);
		});
		String expectedMessage = "400 : [{\"message\":\"Notification recipient invalid\"}]";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}

}
