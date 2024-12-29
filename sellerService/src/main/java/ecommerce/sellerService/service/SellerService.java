package ecommerce.sellerService.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.sellerService.domain.Seller;
import ecommerce.sellerService.exception.EntityNotFoundException;
import ecommerce.sellerService.repository.SellerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {

	private final SellerRepository sellerRepository;

	public Seller registerSeller(Seller seller) {
		return sellerRepository.save(seller);
	}

	public List<Seller> findAllSellers() {
		return sellerRepository.findAll();
	}

	public Seller findById(Long id) {
		return sellerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Seller with ID " + id + " not found"));
	}

	public Seller updateSeller(Long id, Seller updateSeller) {
		Seller seller = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller with ID " + id + " not found"));
		seller.update(updateSeller);
		return seller;
	}

	public void deleteSeller(Long id) {
		sellerRepository.deleteById(id);
	}
}
