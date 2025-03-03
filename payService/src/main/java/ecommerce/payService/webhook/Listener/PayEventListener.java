package ecommerce.payService.webhook.Listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import ecommerce.payService.global.ApiResult;
import ecommerce.payService.webhook.client.OrderClientInterface;
import ecommerce.payService.webhook.event.PayCompletedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayEventListener {

	private final OrderClientInterface orderClient;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePayCompleted(PayCompletedEvent event) {

		ApiResult<Void> result = orderClient.paid(event.getOrderId());
		if (!result.isSuccess()) {
			System.out.println("웹훅 실패");
		}
	}
}
