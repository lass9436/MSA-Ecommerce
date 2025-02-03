package ecommerce.productService.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.store.domain.Store;
import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.store.dto.StoreRequest;
import ecommerce.productService.store.dto.StoreResponse;
import ecommerce.productService.store.repository.StoreRepository;
import ecommerce.productService.client.seller.SellerClient;
import ecommerce.productService.client.seller.Seller;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final SellerClient sellerClient;

	/**
	 * 상점을 등록하는 메서드
	 *
	 * @param storeRequest 상점 등록을 위한 요청 객체
	 * @return 등록된 상점 정보를 담은 StoreResponse 객체
	 */
	public StoreResponse registerStore(StoreRequest storeRequest) {
		// 요청에서 받은 sellerSeq 를 통해 Seller 정보를 조회
		Seller seller = sellerClient.getSellerById(storeRequest.getSellerSeq());

		// Store 객체 생성 후 저장
		Store store = new Store(storeRequest.getStoreName(), storeRequest.getStoreAccountNumber(),
			storeRequest.getStoreLicense(), seller.getSellerSeq());

		// 상점 정보 저장
		storeRepository.save(store);

		// StoreResponse 객체로 변환 후 반환
		return StoreResponse.from(store);
	}

	/**
	 * 모든 상점 목록을 조회하는 메서드
	 *
	 * @return 모든 상점 정보를 담은 StoreResponse 객체 리스트
	 */
	public List<StoreResponse> findAllStores() {
		// 모든 상점을 조회하여 StoreResponse 리스트로 변환 후 반환
		return storeRepository.findAll().stream()
			.map(StoreResponse::from) // 각 Store 객체를 StoreResponse 로 변환
			.toList();
	}

	/**
	 * 상점 ID로 상점 정보를 조회하는 메서드
	 *
	 * @param id 조회할 상점 ID
	 * @return 조회된 상점 정보를 담은 StoreResponse 객체
	 * @throws EntityNotFoundException 상점 ID가 없을 경우 예외 발생
	 */
	public StoreResponse findById(Long id) {
		// 상점 ID로 상점 조회, 없으면 EntityNotFoundException 발생
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found."));

		// 조회된 상점 정보를 StoreResponse 로 반환
		return StoreResponse.from(store);
	}

	/**
	 * 상점 정보를 수정하는 메서드
	 *
	 * @param id 수정할 상점 ID
	 * @param storeRequest 수정할 상점 정보
	 * @return 수정된 상점 정보를 담은 StoreResponse 객체
	 * @throws EntityNotFoundException 상점 ID가 없을 경우 예외 발생
	 */
	public StoreResponse updateStore(Long id, StoreRequest storeRequest) {
		// 요청에서 받은 sellerSeq 를 통해 Seller 정보를 조회
		Seller seller = sellerClient.getSellerById(storeRequest.getSellerSeq());

		// 상점 ID로 상점 조회, 없으면 EntityNotFoundException 발생
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found."));

		// Store 객체의 정보를 업데이트
		store.update(storeRequest.getStoreName(), storeRequest.getStoreAccountNumber(),
			storeRequest.getStoreLicense(), seller.getSellerSeq());

		// 수정된 상점 정보를 StoreResponse 로 반환
		return StoreResponse.from(store);
	}

	/**
	 * 상점을 삭제하는 메서드
	 *
	 * @param id 삭제할 상점 ID
	 * @throws EntityNotFoundException 상점 ID가 없을 경우 예외 발생
	 */
	public void deleteStore(Long id) {
		// 상점 ID로 상점 조회, 없으면 EntityNotFoundException 발생
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found."));

		// 상점 삭제
		storeRepository.delete(store);
	}
}
