package ecommerce.sellerService.controller;

import static ecommerce.sellerService.controller.SellerMapper.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.sellerService.service.SellerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerController {

	private final SellerService sellerService;

	@PostMapping
	public ResponseEntity<SellerResponse> registerSeller(@RequestBody SellerRequest sellerRequest) {
		SellerResponse sellerResponse = toSellerResponse(sellerService.registerSeller(toSeller(sellerRequest)));
		return ResponseEntity.ok(sellerResponse);
	}

	@GetMapping
	public ResponseEntity<List<SellerResponse>> findAllSellers() {
		List<SellerResponse> sellerResponses = sellerService.findAllSellers()
			.stream()
			.map(SellerMapper::toSellerResponse)
			.toList();
		return ResponseEntity.ok(sellerResponses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SellerResponse> findSellerById(@PathVariable Long id) {
		SellerResponse sellerResponse = toSellerResponse(sellerService.findById(id));
		return ResponseEntity.ok(sellerResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SellerResponse> updateSeller(@PathVariable Long id, @RequestBody SellerRequest sellerRequest) {
		SellerResponse sellerResponse = toSellerResponse(sellerService.updateSeller(id, toSeller(sellerRequest)));
		return ResponseEntity.ok(sellerResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
		sellerService.deleteSeller(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}
}
