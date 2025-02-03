package ecommerce.productService.store.controller;

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
import ecommerce.productService.store.dto.StoreRequest;
import ecommerce.productService.store.dto.StoreResponse;
import ecommerce.productService.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	public ApiResult<StoreResponse> registerStore(@Valid @RequestBody StoreRequest storeRequest) {
		return ApiResult.success(storeService.registerStore(storeRequest));
	}

	@GetMapping
	public ApiResult<List<StoreResponse>> findAllStores() {
		return ApiResult.success(storeService.findAllStores());
	}

	@GetMapping("/{id}")
	public ApiResult<StoreResponse> findStoreById(@PathVariable Long id) {
		return ApiResult.success(storeService.findById(id));
	}

	@PutMapping("/{id}")
	public ApiResult<StoreResponse> updateStore(@PathVariable Long id, @Valid @RequestBody StoreRequest storeRequest) {
		return ApiResult.success(storeService.updateStore(id, storeRequest));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteStore(@PathVariable Long id) {
		storeService.deleteStore(id);
		return ApiResult.success(null);
	}

}
