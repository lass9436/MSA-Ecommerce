package ecommerce.sellerService.seller.controller;

import static ecommerce.sellerService.seller.controller.SellerMapper.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.sellerService.global.ApiResult;
import ecommerce.sellerService.seller.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {

	private final SellerService sellerService;

	@PostMapping
	public ApiResult<SellerResponse> registerSeller(@Valid @RequestBody SellerRequest sellerRequest) {
		return ApiResult.success(toSellerResponse(sellerService.registerSeller(toSeller(sellerRequest))));
	}

	@GetMapping
	public ApiResult<List<SellerResponse>> findAllSellers() {
		List<SellerResponse> sellers = sellerService.findAllSellers()
			.stream()
			.map(SellerMapper::toSellerResponse)
			.toList();
		return ApiResult.success(sellers);
	}

	@GetMapping("/{id}")
	public ApiResult<SellerResponse> findSellerById(@PathVariable Long id) {
		return ApiResult.success(toSellerResponse(sellerService.findById(id)));
	}

	@PutMapping("/{id}")
	public ApiResult<SellerResponse> updateSeller(@PathVariable Long id,
		@Valid @RequestBody SellerRequest sellerRequest) {
		return ApiResult.success(toSellerResponse(sellerService.updateSeller(id, toSeller(sellerRequest))));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteSeller(@PathVariable Long id) {
		sellerService.deleteSeller(id);
		return ApiResult.success(null);
	}
}
