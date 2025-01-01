package ecommerce.productService.product.controller;

import static ecommerce.productService.product.controller.StoreMapper.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.productService.product.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ResponseEntity<StoreResponse> registerStore(@Valid @RequestBody StoreRequest storeRequest){
		StoreResponse storeResponse = toStoreResponse(storeService.registerStore(toStore(storeRequest)));
		return ResponseEntity.ok(storeResponse);
	}

}
