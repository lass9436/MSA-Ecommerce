package ecommerce.orderService.messaging.outbox;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventOutbox {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 36)
	private String idempotencyKey;

	@Column(nullable = false, length = 50)
	private String eventName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 7)
	private EventType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 7)
	private EventStatus status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/** 이벤트 데이터 (JSON 형태의 페이로드) */
	@Lob
	@Column(nullable = false)
	private String payload;

	public void success() {
		this.status = EventStatus.SUCCESS;
	}

	public void failure() {
		this.status = EventStatus.FAILURE;
	}

}

