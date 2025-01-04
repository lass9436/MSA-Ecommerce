package ecommerce.productService.product.controller;

import static ecommerce.productService.product.controller.StoreMapper.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.productService.global.ApiResult;
import ecommerce.productService.product.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ApiResult<StoreResponse> registerStore(@Valid @RequestBody StoreRequest storeRequest) {
		return ApiResult.success(toStoreResponse(storeService.registerStore(toStore(storeRequest))));
	}

	@GetMapping
	public ApiResult<List<StoreResponse>> findAllStores() {
		List<StoreResponse> stores = storeService.findAllStores()
			.stream()
			.map(StoreMapper::toStoreResponse)
			.toList();
		return ApiResult.success(stores);
	}

	@GetMapping("/{id}")
	public ApiResult<StoreResponse> findStoreById(@PathVariable Long id) {
		return ApiResult.success(toStoreResponse(storeService.findById(id)));
	}

	@PutMapping("/{id}")
	public ApiResult<StoreResponse> updateStore(@PathVariable Long id, @Valid @RequestBody StoreRequest storeRequest) {
		return ApiResult.success(toStoreResponse(storeService.updateStore(id, toStore(storeRequest))));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteStore(@PathVariable Long id) {
		storeService.deleteStore(id);
		return ApiResult.success(null);
	}

}
