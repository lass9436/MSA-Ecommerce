package ecommerce.sellerService.seller.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.sellerService.seller.domain.Seller;
import ecommerce.sellerService.exception.EntityNotFoundException;
import ecommerce.sellerService.seller.dto.SellerRequest;
import ecommerce.sellerService.seller.dto.SellerResponse;
import ecommerce.sellerService.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {

	private final SellerRepository sellerRepository;

	/**
	 * 판매자 등록 메서드
	 *
	 * @param sellerRequest 판매자 정보를 담고 있는 요청 객체
	 * @return 등록된 판매자 정보에 대한 응답 객체
	 */
	public SellerResponse registerSeller(SellerRequest sellerRequest) {
		// SellerRequest 에서 받은 데이터로 새로운 Seller 객체 생성
		Seller seller = new Seller(sellerRequest.getSellerId(), sellerRequest.getSellerName(), sellerRequest.getSellerPassword());

		// 판매자 정보 저장
		// 판매자 정보를 데이터베이스에 저장
		sellerRepository.save(seller);

		// SellerResponse 객체로 변환 후 반환
		return SellerResponse.from(seller);
	}

	/**
	 * 모든 판매자 정보를 조회하는 메서드
	 *
	 * @return 모든 판매자에 대한 응답 객체 리스트
	 */
	public List<SellerResponse> findAllSellers() {
		// 모든 판매자 정보 조회 후 응답 객체로 변환
		return sellerRepository.findAll().stream()
			.map(SellerResponse::from)
			.toList();
	}

	/**
	 * 특정 ID를 가진 판매자를 조회하는 메서드
	 *
	 * @param id 조회할 판매자의 ID
	 * @return 조회된 판매자에 대한 응답 객체
	 * @throws EntityNotFoundException 판매자가 존재하지 않으면 예외 발생
	 */
	public SellerResponse findById(Long id) {
		// 판매자 ID로 판매자 조회, 없으면 EntityNotFoundException 발생
		Seller seller = sellerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Seller with ID " + id + " not found"));

		// 조회된 판매자 정보를 SellerResponse 로 반환
		return SellerResponse.from(seller);
	}


	/**
	 * 판매자 정보를 수정하는 메서드
	 *
	 * @param id 수정할 판매자의 ID
	 * @param sellerRequest 수정할 판매자 정보가 담긴 요청 객체
	 * @return 수정된 판매자에 대한 응답 객체
	 * @throws EntityNotFoundException 판매자가 존재하지 않으면 예외 발생
	 */
	public SellerResponse updateSeller(Long id, SellerRequest sellerRequest) {
		// 판매자 ID로 조회 후 없으면 예외 발생
		Seller seller = sellerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Seller with ID " + id + " not found"));

		// 판매자 정보 업데이트
		seller.update(sellerRequest.getSellerId(), sellerRequest.getSellerName(), sellerRequest.getSellerPassword());

		// 수정된 판매자 정보를 SellerResponse 로 변환
		return SellerResponse.from(sellerRepository.save(seller));
	}

	/**
	 * 판매자를 삭제하는 메서드
	 *
	 * @param id 삭제할 판매자의 ID
	 * @throws EntityNotFoundException 판매자가 존재하지 않으면 예외 발생
	 */
	public void deleteSeller(Long id) {
		// 판매자 ID로 사용자 조회, 없으면 EntityNotFoundException 발생
		Seller seller = sellerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Seller with ID " + id + " not found"));

		// 판매자 삭제
		sellerRepository.deleteById(id);
	}

}
