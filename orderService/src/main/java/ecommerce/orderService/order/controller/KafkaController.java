package ecommerce.orderService.order.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class KafkaController {

	private final KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/api/test-kafka")
	public void sendMessage(@RequestBody String message) {
		kafkaTemplate.send("test", message);
	}

	@KafkaListener(topics = "test", groupId = "orderService-group")
	public void consumeMessage(String message) {
		System.out.println("kafka message : " + message);
	}
}
